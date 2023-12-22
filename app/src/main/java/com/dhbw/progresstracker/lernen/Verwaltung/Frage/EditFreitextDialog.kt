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
import com.dhbw.progresstracker.databinding.DialogFreitexteditBinding
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.ViewModelFactory
import com.dhbw.progresstracker.repository.database.Frage

class EditFreitextDialog(
    private var myFrage: Frage
) : DialogFragment() {

    private lateinit var viewModel: ViewModel

    private var _binding: DialogFreitexteditBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)


        _binding = DialogFreitexteditBinding.inflate(inflater, container, false)

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

        //Werte setzen
        binding.etFrageFreitext.editText?.setText(myFrage.frage)
        binding.etAntwortFreitext.editText?.setText(myFrage.korrekteAntwort)


        binding.btnSpeichernFreitext.setOnClickListener() {
            editData()
        }

        binding.btnClose2.setOnClickListener() {
            dismiss()
        }
        binding.btnDelete2.setOnClickListener() {
            viewModel.deleteFrage(myFrage)
            dismiss()
        }

    }

    private fun editData() {
        val neueFrage = binding.etFrageFreitext.editText?.text.toString().trim()
        val neueAntwort = binding.etAntwortFreitext.editText?.text.toString().trim()

        if (
            (neueFrage.isNotEmpty()
                    && neueAntwort.isNotEmpty()
                    ) &&
            (myFrage.frage != neueFrage
                    || myFrage.korrekteAntwort != neueAntwort
                    )
            )
        {
            myFrage.frage = neueFrage
            myFrage.korrekteAntwort = neueAntwort

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