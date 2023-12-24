package com.dhbw.progresstracker.freizeit

import OnSwipeTouchListener
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.view.marginTop
import com.dhbw.progresstracker.MainActivity
import com.dhbw.progresstracker.R

class AktivitaetActivity : ComponentActivity() {

    val buttonWidthAdjustmentByPixels = -150
    var aktivitaetName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aktivitaet)

        val zurueckbutton = findViewById<Button>(R.id.zurueckbutton)

        val addButton = findViewById<Button>(R.id.addButton)

        val buttonLayout = findViewById<LinearLayout>(R.id.buttonLayout2)

        val extras = intent.extras
        if (extras != null) {
            for (key in extras.keySet()) {
                val textView = findViewById<TextView>(R.id.aktivitaetTitel)
                textView.text = "$key" // Hier wird der Text entsprechend gesetzt
                aktivitaetName = "$key"
                //val value = extras.get(key)
                //Log.d("AktivitaetActivity", "Key: $key Value: $value")
            }
        }

        loadButtons(buttonLayout, addButton)

        zurueckbutton.setOnClickListener {
            Log.d("AktivitaetActivity", "Hello World von Zurueckbutton!")
            startActivity(Intent(this, FreizeitActivity::class.java))
        }

        addButton.setOnClickListener {
            Log.d("AktivitaetActivity", "Hello World von Addbutton!")
            createButton(buttonLayout, addButton.width + buttonWidthAdjustmentByPixels, addButton.height, addButton.marginTop)

            /*
            val sharedPreferences = getSharedPreferences("ButtonPrefs", Context.MODE_PRIVATE)
            val allEntries: Map<String, *> = sharedPreferences.all

            for ((key, value) in allEntries) {
                Log.d("SharedPreferences", "$key: $value")
            }*/

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
            newButton.setBackgroundResource(R.drawable.bearbeiten_und_delete_icon_farbig_variation)
            buttonLayout.addView(newButton, buttonLayout.childCount - 1)

            newButton.setOnTouchListener(object : OnSwipeTouchListener(this) {
                override fun onSwipeLeft() {
                    // Mülleimer-Icon anzeigen oder Aktion ausführen
                    Log.d("AktivitaetActivity", "Swiped left on button $buttonName")
                    // Button editieren mit Confirmationfenster aufrufen
                    showEditConfirmationDialog("Bitte geben Sie einen neuen Namen ein!", newButton, buttonLayout)
                }
                override fun onSwipeRight() {
                    // Mülleimer-Icon anzeigen oder Aktion ausführen
                    Log.d("AktivitaetActivity", "Swiped right on button $buttonName")

                    // Button entfernen mit Confirmationfenster aufrufen
                    showDeleteConfirmationDialog("Möchten Sie den Button \"" + newButton.text.toString() + "\" wirklich löschen?", newButton)
                }
                override fun onTouch() {
                    // Mülleimer-Icon anzeigen oder Aktion ausführen
                    Log.d("AktivitaetActivity", "Touch/Click on button $buttonName")
                    val sharedPreferences = getSharedPreferences("$aktivitaetName", Context.MODE_PRIVATE)
                    val allEntries: Map<String, *> = sharedPreferences.all

                    for ((key, value) in allEntries) {
                        Log.d("SharedPreferences", "$key: $value")
                    }
                    //showAddDataAsStringDialog("Speichern Sie Ergebnisse!", newButton)
                }
            })

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
        newButton.setBackgroundResource(R.drawable.bearbeiten_und_delete_icon_farbig_variation)
        buttonLayout.addView(newButton, buttonLayout.childCount - 1)

        newButton.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeLeft() {
                // Mülleimer-Icon anzeigen oder Aktion ausführen
                Log.d("AktivitaetActivity", "Swiped left on button $buttonName")
                // Button editieren mit Confirmationfenster aufrufen
                showEditConfirmationDialog("Bitte geben Sie einen neuen Namen ein!", newButton, buttonLayout)
            }
            override fun onSwipeRight() {
                // Mülleimer-Icon anzeigen oder Aktion ausführen
                Log.d("AktivitaetActivity", "Swiped right on button $buttonName")

                // Button entfernen mit Confirmationfenster aufrufen
                showDeleteConfirmationDialog("Möchten Sie den Button \"" + newButton.text.toString() + "\" wirklich löschen?", newButton)
            }
            override fun onTouch() {
                // Mülleimer-Icon anzeigen oder Aktion ausführen
                Log.d("AktivitaetActivity", "Touch/Click on button $buttonName")
                val sharedPreferences = getSharedPreferences("$aktivitaetName", Context.MODE_PRIVATE)
                val allEntries: Map<String, *> = sharedPreferences.all

                for ((key, value) in allEntries) {
                    Log.d("SharedPreferences", "$key: $value")
                }
                //showAddDataAsStringDialog("Speichern Sie Ergebnisse!", newButton)
            }
        })

    }

    private fun showInputDialog(callback: (String) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Namen der neuen Tätigkeit eingeben!")

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

    private fun showDeleteConfirmationDialog(message: String, newButton: Button) {
        val deleteBuilder = AlertDialog.Builder(this)
        deleteBuilder.setMessage(message)
        deleteBuilder.setPositiveButton("OK") { _, _ ->
            deleteButton(newButton)
        }
        deleteBuilder.setNegativeButton("Abbrechen", null)
        deleteBuilder.show()
    }

    private fun showEditConfirmationDialog(message: String, newButton: Button, buttonLayout: LinearLayout) {
        val editBuilder = AlertDialog.Builder(this)
        editBuilder.setTitle(message)
        val input = EditText(this)
        editBuilder.setView(input)
        editBuilder.setPositiveButton("OK") { _, _ ->
            val buttonName = input.text.toString()
            if (!isButtonSaved(buttonName)) {
                editButton(newButton, buttonName, buttonLayout)
            } else {
                showInfoDialog("Dieser Name ist bereits vergeben!")
            }
        }
        editBuilder.setNegativeButton("Abbrechen", null)
        editBuilder.show()
    }

    private fun showAddDataAsStringDialog(message: String, newButton: Button) {
        val editBuilder = AlertDialog.Builder(this)
        editBuilder.setTitle(message)
        val input = EditText(this)
        editBuilder.setView(input)
        editBuilder.setPositiveButton("OK") { _, _ ->
            val value = input.text.toString()
            saveData(value, newButton.text.toString())
        }
        editBuilder.setNegativeButton("Abbrechen", null)
        editBuilder.show()
    }

    private fun saveData(value: String, buttonName: String) {
        val sharedPreferences = getSharedPreferences("$aktivitaetName", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(buttonName, value)
        editor.apply()
    }

    private fun isButtonSaved(buttonName: String): Boolean {
        val sharedPreferences = getSharedPreferences("$aktivitaetName", Context.MODE_PRIVATE)
        return sharedPreferences.contains(buttonName)
    }

    private fun saveButton(buttonName: String) {
        val sharedPreferences = getSharedPreferences("$aktivitaetName", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(buttonName, buttonName)
        editor.apply()
    }

    private fun loadButtons(buttonLayout: LinearLayout, addButton: Button) {
        val sharedPreferences = getSharedPreferences("$aktivitaetName", Context.MODE_PRIVATE)
        val buttonNames = sharedPreferences.all

        // Warte bis der addButton auf dem Bildschirm angezeigt wird
        addButton.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Entferne den Listener, um ihn nicht mehrmals aufzurufen
                addButton.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // Rufe die Funktion zur Erstellung der Buttons auf
                buttonNames?.forEach { buttonName ->
                    createButton(buttonLayout, addButton.width + buttonWidthAdjustmentByPixels, addButton.height, addButton.marginTop, buttonName.value.toString())
                }
            }
        })

        buttonNames?.forEach { buttonName ->
            createButton(buttonLayout, addButton.width + buttonWidthAdjustmentByPixels, addButton.height, addButton.marginTop, buttonName.value.toString())
        }
    }

    private fun deleteButton(button: Button) {
        (button.parent as ViewGroup).removeView(button)
        val sharedPreferences = getSharedPreferences("$aktivitaetName", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(button.text.toString())
        editor.apply()
    }

    private fun editButton(button: Button, newButtonName: String, buttonLayout: LinearLayout) {
        val sharedPreferences = getSharedPreferences("$aktivitaetName", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Entferne den alten Button aus der Ansicht
        (button.parent as ViewGroup).removeView(button)

        // Entferne den alten Button aus den SharedPreferences
        editor.remove(button.text.toString())

        // Speichere den neuen Button mit dem aktualisierten Namen in den SharedPreferences
        editor.putString(newButtonName, newButtonName)
        editor.apply()

        // Erstelle einen neuen Button mit dem aktualisierten Namen und füge ihn der Ansicht hinzu
        createButton(buttonLayout, button.width + buttonWidthAdjustmentByPixels, button.height, button.marginTop, newButtonName)
    }
}