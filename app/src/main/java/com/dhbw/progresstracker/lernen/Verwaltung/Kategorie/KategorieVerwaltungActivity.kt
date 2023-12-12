package com.dhbw.progresstracker.lernen.Verwaltung.Kategorie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.ActivityKategorieverwaltungBinding
import com.dhbw.progresstracker.lernen.Verwaltung.VerwaltungsModusActivity
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.database.Kategorie


class KategorieVerwaltungActivity : AppCompatActivity() {

    var binding: ActivityKategorieverwaltungBinding? = null
    private lateinit var viewModel: ViewModel

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

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        val btnAddFrage = findViewById<ImageButton>(R.id.btnAddFrage)
        btnAddFrage.setOnClickListener {
            Log.d("VerwaltenActivity", "Hello World von btnAddFrage!")
            val frageDialog = FrageDialogInput()
            frageDialog.show(supportFragmentManager, "Neue Frage")
        }

        val btnDeleteKategorie = findViewById<Button>(R.id.btn_DeleteKategorie)
        btnDeleteKategorie.setOnClickListener {
            if (empfangeneKategorie != null) {
                viewModel.delete(empfangeneKategorie)

                startActivity(Intent(this, VerwaltungsModusActivity::class.java))
            }

        }

        val zurueckbutton = findViewById<ImageButton>(R.id.zurueckbutton)

        zurueckbutton.setOnClickListener {
            Log.d("VerwaltenActivity", "Hello World von Zurueckbutton!")
            startActivity(Intent(this, VerwaltungsModusActivity::class.java))
        }
    }


}