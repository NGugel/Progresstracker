package com.dhbw.progresstracker.lernen.Verwaltung.Kategorie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.ActivityKategorieverwaltungBinding
import com.dhbw.progresstracker.lernen.Verwaltung.VerwaltungsModusActivity
import com.dhbw.progresstracker.repository.database.Kategorie


class KategorieVerwaltungActivity : AppCompatActivity() {

    var binding: ActivityKategorieverwaltungBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKategorieverwaltungBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Lese die übergebene Kategorie aus den Intent-Extras aus
        //val kategorie = intent.getParcelableExtra<Kategorie>(EXTRA_KATEGORIE)
        val empfangeneKategorie: Kategorie? = intent.getParcelableExtra("KATEGORIE_KEY")
        Log.d("VerwaltenActivity", "Hello World von KategorieVerwalungActivity die übergebene Kategorie heißt:  ${empfangeneKategorie?.titel}!")

        binding?.kategorieVerwaltungsTitel?.text = "Lernen" + System.getProperty("line.separator") +
                                                    " > " + "${ empfangeneKategorie?.titel}"

        // Jetzt kannst du die ausgewählte Kategorie in dieser Aktivität verwenden
        // ...

        val btnAddFrage = findViewById<ImageButton>(R.id.btnAddFrage)
        btnAddFrage.setOnClickListener {
            Log.d("VerwaltenActivity", "Hello World von btnAddFrage!")
            val frageDialog = FrageDialogInput()
            frageDialog.show(supportFragmentManager, "Neue Frage")
        }

        val zurueckbutton = findViewById<ImageButton>(R.id.zurueckbutton)

        zurueckbutton.setOnClickListener {
            Log.d("VerwaltenActivity", "Hello World von Zurueckbutton!")
            startActivity(Intent(this, VerwaltungsModusActivity::class.java))
        }
    }


}