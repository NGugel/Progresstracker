package com.dhbw.progresstracker.lernen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import com.dhbw.progresstracker.MainActivity
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.lernen.Verwaltung.VerwaltungsModusActivity

class LernenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lernen)

        val zurueckbutton = findViewById<Button>(R.id.zurueckbutton)

        val verwaltungsbutton = findViewById<Button>(R.id.verwaltungsbutton)
        val anwendungsbutton = findViewById<Button>(R.id.anwendungsbutton)

        zurueckbutton.setOnClickListener {
            Log.d("LernenActivity", "Hello World von Zurueckbutton!")
            startActivity(Intent(this, MainActivity::class.java))
        }

        verwaltungsbutton.setOnClickListener {
            Log.d("LernenActivity", "Hello World von Verwaltungsbutton!")
            startActivity(Intent(this, VerwaltungsModusActivity::class.java))
        }

        anwendungsbutton.setOnClickListener {
            Log.d("LernenAcitivity", "Hello World von Anwendungsbutton!")
        }
    }
}