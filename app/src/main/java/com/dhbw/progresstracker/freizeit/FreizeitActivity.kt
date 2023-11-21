package com.dhbw.progresstracker.freizeit

import OnSwipeTouchListener
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import com.dhbw.progresstracker.MainActivity
import com.dhbw.progresstracker.R

class FreizeitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freizeit)

        val zurueckbutton = findViewById<Button>(R.id.zurueckbutton)

        val addButton = findViewById<Button>(R.id.addButton)

        val buttonLayout = findViewById<LinearLayout>(R.id.buttonLayout2)

        loadButtons(buttonLayout, addButton)

        zurueckbutton.setOnClickListener {
            Log.d("FreizeitActivity", "Hello World von Zurueckbutton!")
            startActivity(Intent(this, MainActivity::class.java))
        }

        addButton.setOnClickListener {
            Log.d("FreizeitActivity", "Hello World von Addbutton!")
            createButton(buttonLayout, addButton.width, addButton.height, addButton.marginTop)
        }
    }

    private fun createButton(buttonLayout: LinearLayout, width: Int, height: Int, marginTop: Int) {
        showInputDialog { buttonName ->
            val newButton = Button(this)
            newButton.isAllCaps = false
            newButton.text = buttonName
            newButton.typeface = Typeface.DEFAULT_BOLD
            newButton.layoutParams = LinearLayout.LayoutParams(
                width,
                height
            )
            val layoutParams = newButton.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(0, marginTop, 0, 0) // Abstand zwischen den Buttons festlegen
            newButton.setBackgroundResource(R.drawable.test_herbstbild)
            newButton.setOnClickListener {
                // Aktion für den neuen Button hier definieren
                Log.d("FreizeitActivity", buttonName.toString() )
            }
            buttonLayout.addView(newButton, buttonLayout.childCount - 1)
            saveButton(buttonName)
        }
    }

    private fun createButton(buttonLayout: LinearLayout, width: Int, height: Int, marginTop: Int, buttonName: String) {
        val newButton = Button(this)
        newButton.isAllCaps = false
        newButton.text = buttonName
        newButton.typeface = Typeface.DEFAULT_BOLD
        newButton.layoutParams = LinearLayout.LayoutParams(
            width,
            height
        )
        val layoutParams = newButton.layoutParams as LinearLayout.LayoutParams
        layoutParams.setMargins(0, marginTop, 0, 0) // Abstand zwischen den Buttons festlegen
        newButton.setBackgroundResource(R.drawable.test_herbstbild)

        buttonLayout.addView(newButton, buttonLayout.childCount - 1)

        //todo Listener auch bei anderer createMethode einbauen
        // OnTouchListener, um einen Swipe oder Touch zu erkennen
        newButton.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeLeft() {
                // Mülleimer-Icon anzeigen oder Aktion ausführen
                Log.d("FreizeitActivity", "Swiped left on button $buttonName")

                /*
                // Swipe nach links erkannt - Mülleimer-Icon anzeigen
                val deleteIcon = ContextCompat.getDrawable(this@FreizeitActivity, R.drawable.delete_icon)
                deleteIcon?.setBounds(newButton.width - newButton.width / 4 - deleteIcon.intrinsicWidth, 0, newButton.width / 4, newButton.height * 3 / 4)
                newButton.setCompoundDrawables(deleteIcon, null, null, null)

                 */

                /*
                // Button rechts anzeigen
                val layoutParams = newButton.layoutParams as RelativeLayout.LayoutParams
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
                newButton.layoutParams = layoutParams

                 */
            }
            override fun onSwipeRight() {
                // Mülleimer-Icon anzeigen oder Aktion ausführen
                Log.d("FreizeitActivity", "Swiped right on button $buttonName")

                // Button entfernen
                deleteButton(newButton)
            }
            override fun onTouch() {
                // Mülleimer-Icon anzeigen oder Aktion ausführen
                Log.d("FreizeitActivity", "Touch/Click on button $buttonName")
                val sharedPreferences = getSharedPreferences("ButtonPrefs", Context.MODE_PRIVATE)
                val allEntries: Map<String, *> = sharedPreferences.all

                for ((key, value) in allEntries) {
                    Log.d("SharedPreferences", "$key: $value")
                }
            }
        })

    }

    private fun showInputDialog(callback: (String) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Namen der neuen Aktivität eingeben!")

        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            val buttonName = input.text.toString()
            if (!isButtonSaved(buttonName)) {
                callback(buttonName)
            } else {
                showInfoDialog("Dieser Name ist bereits vergeben!")
            }
        }

        builder.setNegativeButton("Abbrechen") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun showInfoDialog(message: String) {
        val infoBuilder = AlertDialog.Builder(this)
        infoBuilder.setMessage(message)
        infoBuilder.setPositiveButton("OK", null)
        infoBuilder.show()
    }

    private fun isButtonSaved(buttonName: String): Boolean {
        val sharedPreferences = getSharedPreferences("ButtonPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.contains(buttonName)
    }

    private fun saveButton(buttonName: String) {
        val sharedPreferences = getSharedPreferences("ButtonPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(buttonName, buttonName)
        editor.apply()
    }

    private fun loadButtons(buttonLayout: LinearLayout, addButton: Button) {
        val sharedPreferences = getSharedPreferences("ButtonPrefs", Context.MODE_PRIVATE)
        val buttonNames = sharedPreferences.all

        // Warte bis der addButton auf dem Bildschirm angezeigt wird
        addButton.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Entferne den Listener, um ihn nicht mehrmals aufzurufen
                addButton.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // Rufe die Funktion zur Erstellung der Buttons auf
                buttonNames?.forEach { buttonName ->
                    createButton(buttonLayout, addButton.width, addButton.height, addButton.marginTop, buttonName.value.toString())
                }
            }
        })

        buttonNames?.forEach { buttonName ->
            createButton(buttonLayout, addButton.width, addButton.height, addButton.marginTop, buttonName.value.toString())
        }
    }

    private fun deleteButton(button: Button) {
        (button.parent as ViewGroup).removeView(button)
        val sharedPreferences = getSharedPreferences("ButtonPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(button.text.toString())
        editor.apply()
    }

    private fun getButtonNamesFromLayout(buttonLayout: LinearLayout): List<String> {
        val buttonNames = mutableListOf<String>()
        for (i in 0 until buttonLayout.childCount - 1) {
            val button = buttonLayout.getChildAt(i) as Button
            buttonNames.add(button.text.toString())
        }
        return buttonNames
    }

}