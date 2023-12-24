package com.dhbw.progresstracker.lernen.Anwendungsmodus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.databinding.FragmentFreitextBeantwortenBinding
import com.dhbw.progresstracker.databinding.FragmentFreitextBinding
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.ViewModelFactory
import com.dhbw.progresstracker.repository.database.Frage
import com.dhbw.progresstracker.repository.database.Fragetyp




class FreitextbeantwortenFragment(private var myFrage: Frage) : Fragment() {

    private var _binding: FragmentFreitextBeantwortenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFreitextBeantwortenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFrageFreitext.text = myFrage.frage

        binding.btnSubmit.setOnClickListener(){
           val eingegebeneAntwort = binding.etAntwortFreitext.editText?.text.toString()

            if(überprüfeAntwort(eingegebeneAntwort)){
                Toast.makeText(requireContext(), "Korrekte Antwort, super!", Toast.LENGTH_SHORT).show()
                Log.d("Freitextbeantworten", "Hello World von FreitextbeantwortenFragment, Antwort ist korrekt")

                //Fragment zerstören
                parentFragmentManager?.beginTransaction()?.remove(this)?.commit()

            } else {
                Toast.makeText(requireContext(), "Falsche Antwort", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSkip.setOnClickListener(){
            parentFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }

    fun überprüfeAntwort(eingegebeneAntwort: String): Boolean {
        val korrekteAntwort = myFrage.korrekteAntwort
        return eingegebeneAntwort.equals(korrekteAntwort, ignoreCase = true)
    }
}
