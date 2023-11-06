package com.dhbw.progresstracker.freizeit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import com.dhbw.progresstracker.MainActivity
import com.dhbw.progresstracker.R

class FreizeitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freizeit)

        val zurueckbutton = findViewById<Button>(R.id.zurueckbutton)

        val verwaltungsbutton = findViewById<Button>(R.id.verwaltungsbutton)
        val anwendungsbutton = findViewById<Button>(R.id.anwendungsbutton)

        zurueckbutton.setOnClickListener {
            Log.d("FreizeitActivity", "Hello World von Zurueckbutton!")
            startActivity(Intent(this, MainActivity::class.java))
        }

        verwaltungsbutton.setOnClickListener {
            Log.d("FreizeitActivity", "Hello World von Verwaltungsbutton!")
        }

        anwendungsbutton.setOnClickListener {
            Log.d("FreizeitActivity", "Hello World von Anwendungsbutton!")
        }
    }
}