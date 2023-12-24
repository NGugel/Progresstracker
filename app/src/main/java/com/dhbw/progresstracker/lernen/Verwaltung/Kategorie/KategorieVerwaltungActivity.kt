package com.dhbw.progresstracker.lernen.Verwaltung.Kategorie

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
import com.dhbw.progresstracker.lernen.Verwaltung.Frage.EditFehlertextDialog
import com.dhbw.progresstracker.lernen.Verwaltung.Frage.EditFreitextDialog
import com.dhbw.progresstracker.lernen.Verwaltung.Frage.EditMultipleChoiceDialog
import com.dhbw.progresstracker.lernen.Verwaltung.Frage.FrageAdapter
import com.dhbw.progresstracker.lernen.Verwaltung.VerwaltungsModusActivity
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.database.Frage
import com.dhbw.progresstracker.repository.database.Fragetyp
import com.dhbw.progresstracker.repository.database.Kategorie


class KategorieVerwaltungActivity : AppCompatActivity() {

    var binding: ActivityKategorieverwaltungBinding? = null
    private lateinit var viewModel: ViewModel
    private lateinit var adapter: FrageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initRecyclerView()

        // Lese die übergebene Kategorie aus den Intent-Extras aus
        val empfangeneKategorie: Kategorie? = intent.getParcelableExtra("KATEGORIE_KEY")
        Log.d("VerwaltenActivity", "Hello World von KategorieVerwalungActivity die übergebene Kategorie heißt:  ${empfangeneKategorie?.titel}!")

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.filteredFragen.observe(this, Observer { fragen ->
            Log.d("VerwaltenActivity", "Hello World von FragenLiveDataObserver")
            adapter?.updateContent(ArrayList(fragen))
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

        Log.d("KategorieActivity", "Hello World von OnItemClick, Recyclerview ClickListener - die Frage heißt:  ${selectedFrage.frage}!")

        when(selectedFrage.fragetyp) {

            Fragetyp.MULTIPLE_CHOICE -> {
                val editMultipleChoiceDialog = EditMultipleChoiceDialog(selectedFrage)

                editMultipleChoiceDialog.show(supportFragmentManager, "Edit MultipleChoice Frage")

            }
            Fragetyp.FREITEXT -> {
                val editFreitextDialog = EditFreitextDialog(selectedFrage)

                editFreitextDialog.show(supportFragmentManager, "Edit Freitext Frage")

            }

            Fragetyp.FEHLERTEXT -> {
                val editFehlertextDialog = EditFehlertextDialog(selectedFrage)

                editFehlertextDialog.show(supportFragmentManager, "Edit Freitext Frage")
            }

        }

    }


}