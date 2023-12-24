package com.dhbw.progresstracker.lernen.Verwaltung

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.ActivityVerwaltungBinding
import com.dhbw.progresstracker.lernen.LernenActivity
import com.dhbw.progresstracker.lernen.Verwaltung.Kategorie.KategorieAdapter
import com.dhbw.progresstracker.lernen.Verwaltung.Kategorie.KategorieDialogInput
import com.dhbw.progresstracker.lernen.Verwaltung.Kategorie.KategorieVerwaltungActivity
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.database.Kategorie


class VerwaltungsModusActivity : AppCompatActivity() {


    var binding: ActivityVerwaltungBinding? = null
    private lateinit var adapter: KategorieAdapter
    private lateinit var viewModel: ViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("VerwaltungsModusActivity", "Hello World von VerwaltungsmodusActivity!")


        initRecyclerView()
        //ViewModel instanzieren
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.getLiveDataKategorien().observe(this, Observer { kategorien ->
            Log.d("VerwaltenActivity", "Hello World von KategorienLiveDataObserver")
            adapter?.updateContent(ArrayList(kategorien))
        }

        )

        val btnAddKategorie = findViewById<ImageButton>(R.id.btnAddKategorie)
        btnAddKategorie.setOnClickListener {
            Log.d("VerwaltenActivity", "Hello World von btnAddKategorie!")
            val kategorieDialog = KategorieDialogInput()
            kategorieDialog.show(supportFragmentManager, "Neue Kategorie")
        }

        val zurueckbutton = findViewById<ImageButton>(R.id.zurueckbutton)

        zurueckbutton.setOnClickListener {
            Log.d("VerwaltenActivity", "Hello World von Zurueckbutton!")
            startActivity(Intent(this, LernenActivity::class.java))
        }

    }

    fun initRecyclerView() {

        binding = ActivityVerwaltungBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        adapter = KategorieAdapter(ArrayList(), ::onItemClick)
        binding?.rvKategorie?.adapter = adapter
    }

    private fun onItemClick(selectedKategorie: Kategorie) {
        // Implementiere hier die Logik für den Klick auf ein Element
        // Zum Beispiel: Öffne einen neuen Bildschirm mit den Details der ausgewählten Kategorie
        Log.d("VerwaltenActivity", "Hello World von OnItemClick, Recyclerview ClickListener - die Kategorie heißt:  ${selectedKategorie.titel}!")
        val intent = Intent(this, KategorieVerwaltungActivity::class.java)
        intent.putExtra("KATEGORIE_KEY", selectedKategorie)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}