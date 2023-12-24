package com.dhbw.progresstracker.lernen.Anwendungsmodus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.DialogFrageinputBinding
import com.dhbw.progresstracker.databinding.DialogFragenanzahlBinding
import com.dhbw.progresstracker.lernen.Verwaltung.Kategorie.FrageDialogInput
import com.dhbw.progresstracker.lernen.Verwaltung.Kategorie.KategorieVerwaltungActivity
import com.dhbw.progresstracker.repository.ViewModel
import kotlin.concurrent.thread

class FrageanzahlDialog : DialogFragment() {

    // ViewModel
    private lateinit var viewModel: ViewModel

    private var _binding: DialogFragenanzahlBinding? = null
    private val binding get() = _binding!!

    private var myFragenanzahl = 1

    companion object {
        const val EXTRA_KATEGORIE_ID = "extra_kategorie_id"
        const val EXTRA_KATEGORIE_TITEL = "extra_kategorie_titel"
    }

    var empfangeneKategorieId: Int = 0
    var empfangeneKategorieTitel: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)

        arguments?.let {
            empfangeneKategorieId = it.getInt(EXTRA_KATEGORIE_ID)
            empfangeneKategorieTitel = it.getString(EXTRA_KATEGORIE_TITEL)

            Log.d(
                "FrageanzahlDialog",
                "Hello World von FrageanzahlDialog die übergebene KategorieId heißt:  ${empfangeneKategorieId} und der Titel: ${empfangeneKategorieTitel}"
            )
            // Jetzt kannst du empfangeneKategorie in diesem Fragment verwenden
            // ...
        }

        //ViewModel instanzieren
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        _binding = DialogFragenanzahlBinding.inflate(inflater, container, false)

        binding.tvSelectedKategorie.text = "Ausgewählte Katgorie: ${empfangeneKategorieTitel}"





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvProgress.text = progress.toString()
                myFragenanzahl = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        }
        )

        viewModel.filteredFragen.observe(this, Observer { fragen ->
            // Update your UI with the filtered list of Fragen

            Log.d("FrageanzahlDialog", "Hello World von FragenLiveDataObserver")
            binding.tvMax.text = fragen.count().toString()
            binding.seekBar.max = fragen.count()
            // The 'fragen' variable now contains the filtered list
            // You can do whatever you need to do with this list
        })

        //Check ob alle Kategorien oder nur eine Kategorie, fragen anhand dessen anders abfragen

        if(empfangeneKategorieId == 0){ //Alle Kategorien

            viewModel.getLiveDataFragen().observe(this, Observer { fragen ->

                Log.d("FrageanzahlDialog", "Hello World von FragenLiveDataObserver")
                binding.tvMax.text = fragen.count().toString()
                binding.seekBar.max = fragen.count()

            })


        } //Kategorie wurde ausgewählt
        else {

            viewModel.filteredFragen.observe(this, Observer { fragen ->
                // Update your UI with the filtered list of Fragen

                Log.d("FrageanzahlDialog", "Hello World von FragenLiveDataObserver")
                binding.tvMax.text = fragen.count().toString()
                binding.seekBar.max = fragen.count()

            })

            // Trigger the filtering by calling getLiveDataFragenByKategorie with the desired kategorieId
            viewModel.getLiveDataFragenByKategorie(empfangeneKategorieId)
        }

        //Fragebeantworten starten

        binding.dialogBtnStarten.setOnClickListener(){
            val intent = Intent(activity, FragenbeantwortenActivity::class.java)
            intent.putExtra("KATEGORIE_KEY", empfangeneKategorieId)
            intent.putExtra("FRAGENANZAHL", myFragenanzahl)
            startActivity(intent)

        }

        binding.dialogBtnAbbrechen.setOnClickListener(){
            dismiss()
        }

    }
}