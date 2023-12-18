package com.dhbw.progresstracker.lernen.Verwaltung.Kategorie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.ActivityKategorieverwaltungBinding
import com.dhbw.progresstracker.databinding.ActivityVerwaltungBinding
import com.dhbw.progresstracker.lernen.Verwaltung.Frage.FrageAdapter
import com.dhbw.progresstracker.lernen.Verwaltung.VerwaltungsModusActivity
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.database.Frage
import com.dhbw.progresstracker.repository.database.Kategorie


class KategorieVerwaltungActivity : AppCompatActivity() {

    var binding: ActivityKategorieverwaltungBinding? = null
    private lateinit var viewModel: ViewModel
    private lateinit var adapter: FrageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initRecyclerView()

        // Lese die übergebene Kategorie aus den Intent-Extras aus
        //val kategorie = intent.getParcelableExtra<Kategorie>(EXTRA_KATEGORIE)
        val empfangeneKategorie: Kategorie? = intent.getParcelableExtra("KATEGORIE_KEY")
        Log.d("VerwaltenActivity", "Hello World von KategorieVerwalungActivity die übergebene Kategorie heißt:  ${empfangeneKategorie?.titel}!")

        binding?.kategorieVerwaltungsTitel?.text = "Lernen" + System.getProperty("line.separator") +
                                                    " > " + "${ empfangeneKategorie?.titel}"

        // Jetzt kannst du die ausgewählte Kategorie in dieser Aktivität verwenden
        // ...

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.filteredFragen.observe(this, Observer { fragen ->
            // Update your UI with the filtered list of Fragen

            Log.d("VerwaltenActivity", "Hello World von FragenLiveDataObserver")
            adapter?.updateContent(ArrayList(fragen))
            // The 'fragen' variable now contains the filtered list
            // You can do whatever you need to do with this list
        })

        // Trigger the filtering by calling getLiveDataFragenByKategorie with the desired kategorieId
        val kategorieId = empfangeneKategorie!!.id
        viewModel.getLiveDataFragenByKategorie(kategorieId)


        val btnAddFrage = findViewById<ImageButton>(R.id.btnAddFrage)
        btnAddFrage.setOnClickListener {
            Log.d("VerwaltenActivity", "Hello World von btnAddFrage!")
            val frageDialog = FrageDialogInput()

            val bundle = Bundle()
            bundle.putInt(FrageDialogInput.EXTRA_KATEGORIE, empfangeneKategorie.id)
            frageDialog.arguments = bundle

            frageDialog.show(supportFragmentManager, "Neue Frage")
        }

        val btnEditKategorie = findViewById<Button>(R.id.btn_RenameKategorie)
        btnEditKategorie.setOnClickListener {
            Log.d("KategorieVerwaltenActivity", "Hello World von btnEditFrage!")
            val kategorieEditDialog = KategorieDialogEdit()

            val bundle = Bundle()
            bundle.putInt(KategorieDialogEdit.EXTRA_KATEGORIE, empfangeneKategorie.id)
            bundle.putString(KategorieDialogEdit.EXTRA_KATEGORIETITEL, empfangeneKategorie.titel)
            kategorieEditDialog.arguments = bundle

            kategorieEditDialog.show(supportFragmentManager, "Kategorie bearbeiten")
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

    fun initRecyclerView() {

        binding = ActivityKategorieverwaltungBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        adapter = FrageAdapter(ArrayList(), ::onItemClick)
        binding?.rvFragen?.adapter = adapter
    }

    private fun onItemClick(selectedFrage: Frage) {
        // Implementiere hier die Logik für den Klick auf ein Element
        // Zum Beispiel: Öffne einen neuen Bildschirm mit den Details der ausgewählten Kategorie
        Log.d("VerwaltenActivity", "Hello World von OnItemClick, Recyclerview ClickListener - die Kategorie heißt:  ${selectedFrage.frage}!")
       // val intent = Intent(this, KategorieVerwaltungActivity::class.java)
        //intent.putExtra("KATEGORIE_KEY", selectedFrage)
       // startActivity(intent)
    }


}