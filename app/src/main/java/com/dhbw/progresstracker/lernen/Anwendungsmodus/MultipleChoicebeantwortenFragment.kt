package com.dhbw.progresstracker.lernen.Anwendungsmodus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dhbw.progresstracker.databinding.FragmentMultiplechoiceBeantwortenBinding
import com.dhbw.progresstracker.repository.database.Frage

class MultipleChoicebeantwortenFragment(private var myFrage: Frage): Fragment() {

    private var _binding: FragmentMultiplechoiceBeantwortenBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMultiplechoiceBeantwortenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFrageMultipleChoice.text = myFrage.frage
        binding.tvAntwortA.text = "A: ${myFrage.antwortA}"
        binding.tvAntwortB.text = "B: ${myFrage.antwortB}"
        binding.tvAntwortC.text = "C: ${myFrage.antwortC}"
        binding.tvAntwortD.text = "D: ${myFrage.antwortD}"

        binding.btnSubmitMultipleChoice.setOnClickListener() {

            if (überprüfeAntwort(getSpinnerValue())) {
                Toast.makeText(requireContext(), "Korrekte Antwort, super!", Toast.LENGTH_SHORT)
                    .show()
                Log.d(
                    "MultipleChpiocebeantworten",
                    "Hello World von MultipleChoicebeantwortenFragment, Antwort ist korrekt"
                )

                //Fragment zerstören
                parentFragmentManager?.beginTransaction()?.remove(this)?.commit()

            } else {
                Toast.makeText(requireContext(), "Falsche Antwort", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSkip.setOnClickListener() {
            parentFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }

    fun überprüfeAntwort(eingegebeneAntwort: String): Boolean {
        val korrekteAntwort = myFrage.korrekteAntwort
        return eingegebeneAntwort.equals(korrekteAntwort, ignoreCase = true)
    }

    fun getSpinnerValue(): String {
        var ausgewählteAntwort = ""

        if (binding.spinnerAntwort.selectedItemPosition == 0) {
            ausgewählteAntwort = myFrage.antwortA.toString()
        }
        if (binding.spinnerAntwort.selectedItemPosition == 1) {
            ausgewählteAntwort = myFrage.antwortB.toString()
        }
        if (binding.spinnerAntwort.selectedItemPosition == 2) {
            ausgewählteAntwort = myFrage.antwortC.toString()
        }
        if (binding.spinnerAntwort.selectedItemPosition == 3)
        {
            ausgewählteAntwort = myFrage.antwortD.toString()
        }
        return ausgewählteAntwort
    }
}
