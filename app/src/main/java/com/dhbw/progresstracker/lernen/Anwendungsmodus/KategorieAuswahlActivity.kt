package com.dhbw.progresstracker.lernen.Anwendungsmodus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.ActivityAuswahlBinding
import com.dhbw.progresstracker.lernen.LernenActivity
import com.dhbw.progresstracker.lernen.Verwaltung.Kategorie.KategorieAdapter
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.database.Kategorie

class KategorieAuswahlActivity : AppCompatActivity() {


    var binding: ActivityAuswahlBinding? = null
    private lateinit var adapter: KategorieAdapter
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Kategorieauswahl", "Hello World von KategorieauswahlActivity!")


        initRecyclerView()

        //ViewModel instanzieren
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.getLiveDataKategorien().observe(this, Observer { kategorien ->
            Log.d("KategorieAuswahl", "Hello World von KategorienLiveDataObserver")
            adapter?.updateContent(ArrayList(kategorien))
        }

        )

        binding?.btnAll?.setOnClickListener(){

            val frageanzahlDialog = FrageanzahlDialog()

            val bundle = Bundle()
            bundle.putInt(FrageanzahlDialog.EXTRA_KATEGORIE_ID, 0)
            bundle.putString(FrageanzahlDialog.EXTRA_KATEGORIE_TITEL, "Alle")
            frageanzahlDialog.arguments = bundle

            frageanzahlDialog.show(supportFragmentManager, "Fragenanzahl auswählen")
        }

        val zurueckbutton = findViewById<ImageButton>(R.id.zurueckbutton)

        zurueckbutton.setOnClickListener {
            Log.d("KategorieAuswahl", "Hello World von Zurueckbutton!")
            startActivity(Intent(this, LernenActivity::class.java))
        }

    }

    fun initRecyclerView() {

        binding = ActivityAuswahlBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        adapter = KategorieAdapter(ArrayList(), ::onItemClick)
        binding?.rvKategorie?.adapter = adapter
    }

    private fun onItemClick(selectedKategorie: Kategorie) {
        Log.d(
            "KategorieAuswahl",
            "Hello World von OnItemClick, Recyclerview ClickListener - die Kategorie heißt:  ${selectedKategorie.titel}!"
        )

        val frageanzahlDialog = FrageanzahlDialog()

        val bundle = Bundle()
        bundle.putInt(FrageanzahlDialog.EXTRA_KATEGORIE_ID, selectedKategorie.id)
        bundle.putString(FrageanzahlDialog.EXTRA_KATEGORIE_TITEL, selectedKategorie.titel)
        frageanzahlDialog.arguments = bundle

        frageanzahlDialog.show(supportFragmentManager, "Fragenanzahl auswählen")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}