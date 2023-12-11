package com.dhbw.progresstracker.lernen.Verwaltung

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.MainActivity
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.ActivityVerwaltungBinding
import com.dhbw.progresstracker.lernen.Verwaltung.Kategorie.KategorieAdapter
import com.dhbw.progresstracker.lernen.Verwaltung.Kategorie.KategorieDialogInput
import com.dhbw.progresstracker.lernen.Verwaltung.Kategorie.KategorieViewModel


class VerwaltungsModusActivity : AppCompatActivity() {


    var binding: ActivityVerwaltungBinding? = null
    private lateinit var adapter: KategorieAdapter
    private lateinit var kategorieViewModel: KategorieViewModel

    /*
        //create Viewmodel
        private val kategorieViewModel: KategorieViewModel by viewModels {
            KategorieViewModelFactory((application as Application).repository)
        }
        */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("VerwaltungsModusActivity", "Hello World von VerwaltungsmodusActivity!")

        //setContentView(R.layout.activity_verwaltung)

        initRecyclerView()
        //ViewModel instanzieren
        kategorieViewModel = ViewModelProvider(this).get(KategorieViewModel::class.java)

        kategorieViewModel.getLiveDataKategorien().observe(this, Observer { kategorien ->
            Log.d("VerwaltenActivity", "Hello World von KategorienLiveDataObserver")
            adapter?.updateContent(ArrayList(kategorien))
        }

        )

        //dieser layoutmanager zuweisung oder direkt im xml unter der recycler view zum anordnen der items
        //binding?.rvKategorie?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        val btnAddKategorie = findViewById<Button>(R.id.btnAddKategorie)
        btnAddKategorie.setOnClickListener {
            Log.d("VerwaltenActivity", "Hello World von btnAddKategorie!")
            val kategorieDialog = KategorieDialogInput()
            kategorieDialog.show(supportFragmentManager, "Neue Kategorie")
        }

        /* kategorieViewModel.allKategorien.observe(this, Observer { items ->
             // Update the cached copy of the words in the adapter.
             adapter.updateContent(ArrayList(items))
         })*/


        val zurueckbutton = findViewById<Button>(R.id.zurueckbutton)

        zurueckbutton.setOnClickListener {
            Log.d("VerwaltenActivity", "Hello World von Zurueckbutton!")
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    fun initRecyclerView() {

        binding = ActivityVerwaltungBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        adapter = KategorieAdapter(ArrayList())
        binding?.rvKategorie?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}