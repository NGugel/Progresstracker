package com.dhbw.progresstracker.freizeit

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
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
        val newButton = Button(this)
        newButton.text = "Neuer Button"
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
        }
        buttonLayout.addView(newButton, buttonLayout.childCount - 1)
    }
}