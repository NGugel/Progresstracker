package com.dhbw.progresstracker.freizeit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.core.view.marginTop
import com.dhbw.progresstracker.MainActivity
import com.dhbw.progresstracker.R

class AktivitaetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aktivitaet)

        val zurueckbutton = findViewById<Button>(R.id.zurueckbutton)

        val addButton = findViewById<Button>(R.id.addButton)

        val buttonLayout = findViewById<LinearLayout>(R.id.buttonLayout2)

        zurueckbutton.setOnClickListener {
            Log.d("AktivitaetActivity", "Hello World von Zurueckbutton!")
            startActivity(Intent(this, FreizeitActivity::class.java))
        }

        addButton.setOnClickListener {
            Log.d("AktivitaetActivity", "Hello World von Addbutton!")
            val sharedPreferences = getSharedPreferences("ButtonPrefs", Context.MODE_PRIVATE)
            val allEntries: Map<String, *> = sharedPreferences.all

            for ((key, value) in allEntries) {
                Log.d("SharedPreferences", "$key: $value")
            }

            val extras = intent.extras
            if (extras != null) {
                for (key in extras.keySet()) {
                    val value = extras.get(key)
                    Log.d("AktivitaetActivity", "Key: $key Value: $value")
                }
            }


        }
    }
}