package com.dhbw.progresstracker.lernen.Verwaltung.Frage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.databinding.FragmentFreitextBinding
import com.dhbw.progresstracker.databinding.FragmentMultiplechoiceBinding
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.ViewModelFactory
import com.dhbw.progresstracker.repository.database.Fragetyp

class FreitextFragment: Fragment() {


    private var _binding: FragmentFreitextBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ViewModel

    private lateinit var btnSave: Button

    companion object {
        const val EXTRA_KATEGORIE = "extra_kategorie"
    }

    var empfangeneKategorieId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFreitextBinding.inflate(inflater, container, false)


        arguments?.let {
            empfangeneKategorieId = it.getInt(EXTRA_KATEGORIE)

            Log.d(
                "MultipleChooiceFragment",
                "Hello World von MultipleChoiceFragment die übergebene KategorieId heißt:  ${empfangeneKategorieId}!"
            )
            // Jetzt kannst du empfangeneKategorie in diesem Fragment verwenden
            // ...
        }





        btnSave = binding.btnSpeichernFreitext

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireActivity().application)
        ).get(ViewModel::class.java)

        btnSave.setOnClickListener {
            saveData()
        }

    }

    private fun saveData() {
        val frage = binding.etFrageFreitext.editText?.text.toString()
        val korrekteAntwort = binding.etAntwortFreitext.editText?.text.toString()


        if (frage.isEmpty() || korrekteAntwort.isEmpty()) {
            // Zeige Toast-Meldung für leere Felder
            Toast.makeText(requireContext(), "Bitte fülle alle Felder aus", Toast.LENGTH_SHORT).show()
        } else {
            // Hier kannst du die nicht-leeren Eingaben verwenden und speichern
            viewModel.insertFrage(empfangeneKategorieId, frage, null, null, null, null,null, korrekteAntwort, Fragetyp.FREITEXT)
            Toast.makeText(requireContext(), "Frage wurde erfolgreich gespeichert", Toast.LENGTH_SHORT).show()
            Log.d("Freitextfragment", "Hello World von FreitextFragnent!! Frage wurde gespeichert:  ${empfangeneKategorieId} und ${frage}  und ${korrekteAntwort} korrekte An und ${Fragetyp.FREITEXT}")
        }
    }




}