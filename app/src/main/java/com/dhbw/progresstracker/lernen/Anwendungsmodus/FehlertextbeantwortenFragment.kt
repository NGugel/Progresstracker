package com.dhbw.progresstracker.lernen.Anwendungsmodus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dhbw.progresstracker.databinding.FragmentFehlertextBeantwortenBinding
import com.dhbw.progresstracker.repository.database.Frage

class FehlertextbeantwortenFragment(private var myFrage: Frage) : Fragment() {

    private var _binding: FragmentFehlertextBeantwortenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFehlertextBeantwortenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFrageFehlerText2.text = myFrage.frage
        binding.richEditorFalscherText.html = myFrage.fehlerAntwort

        binding.btnSubmit.setOnClickListener(){
            val eingegebeneAntwort = binding.richEditorFalscherText.html

            if(überprüfeAntwort(eingegebeneAntwort)){
                Toast.makeText(requireContext(), "Korrekte Antwort, super!", Toast.LENGTH_SHORT).show()
                Log.d("Fehlertextbeantworten", "Hello World von FehlertextbeantwortenFragment, Antwort ist korrekt")

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
