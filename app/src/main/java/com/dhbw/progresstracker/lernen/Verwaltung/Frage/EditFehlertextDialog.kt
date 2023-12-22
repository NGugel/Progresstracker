package com.dhbw.progresstracker.lernen.Verwaltung.Frage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.DialogFehlertexteditBinding
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.ViewModelFactory
import com.dhbw.progresstracker.repository.database.Frage


class EditFehlertextDialog(
    private var myFrage: Frage
) : DialogFragment() {

    private lateinit var viewModel: ViewModel

    private var _binding: DialogFehlertexteditBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)


        _binding = DialogFehlertexteditBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireActivity().application)
        ).get(ViewModel::class.java)


        Log.d(
            "EditFreitext",
            "Hello World von EditFreitext die übergebene Frage heißt:  ${myFrage?.frage}"
        )


        binding.etFrageFehlerText.editText?.setText(myFrage.frage)
        binding.richEditorFalscherText.html = myFrage.fehlerAntwort
        binding.richEditorKorrekterText.html = myFrage.korrekteAntwort


        binding.btnSpeichernFehlerText.setOnClickListener() {
            editData()
        }

        binding.btnClose3.setOnClickListener() {
            dismiss()
        }
        binding.btnDelete3.setOnClickListener() {
            viewModel.deleteFrage(myFrage)
            dismiss()
        }

    }

    private fun editData() {
        val neueFrage = binding.etFrageFehlerText.editText?.text.toString().trim()
        val neueFalscheAntwort = binding.richEditorFalscherText.html.trim()
        val neueKorrekteAntwort = binding.richEditorKorrekterText.html.trim()

        if (
            (neueFrage.isNotEmpty()
                    && neueFalscheAntwort.isNotEmpty()
                    && neueKorrekteAntwort.isNotEmpty()
                    ) &&
            (myFrage.frage != neueFrage
                    || myFrage.fehlerAntwort != neueFalscheAntwort
                    || myFrage.korrekteAntwort != neueKorrekteAntwort
                    )
        )
        {
            myFrage.frage = neueFrage
            myFrage.fehlerAntwort = neueFalscheAntwort
            myFrage.korrekteAntwort = neueKorrekteAntwort

            viewModel.updateFrage(myFrage)

            Toast.makeText(
                requireContext(),
                "Frage wurde erfolgreich geändert",
                Toast.LENGTH_SHORT
            ).show()
            dismiss()

        } else {
            Toast.makeText(requireContext(), "Bitte zuerst etwas ändern", Toast.LENGTH_SHORT)
                .show()
        }
    }


}