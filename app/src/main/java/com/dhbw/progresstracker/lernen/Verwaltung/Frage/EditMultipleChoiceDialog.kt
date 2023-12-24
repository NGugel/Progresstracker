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
import com.dhbw.progresstracker.databinding.DialogMultiplechoiceeditBinding
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.ViewModelFactory
import com.dhbw.progresstracker.repository.database.Frage

class EditMultipleChoiceDialog(
    private var myFrage: Frage
) : DialogFragment() {

    private lateinit var viewModel: ViewModel

    private var _binding: DialogMultiplechoiceeditBinding? = null
    private val binding get() = _binding!!

    private var originalSpinnerPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)


        _binding = DialogMultiplechoiceeditBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireActivity().application)
        ).get(ViewModel::class.java)


        Log.d(
            "EditMultipleChoice",
            "Hello World von EditMultipleChoice die übergebene Frage heißt:  ${myFrage?.frage}"
        )

        //Werte setzen
        setSpinner()
        binding.etFrageMultipleChoice.editText?.setText(myFrage.frage)
        binding.etAntwortA.editText?.setText(myFrage.antwortA)
        binding.etAntwortB.editText?.setText(myFrage.antwortB)
        binding.etAntwortC.editText?.setText(myFrage.antwortC)
        binding.etAntwortD.editText?.setText(myFrage.antwortD)

        binding.btnSpeichern.setOnClickListener() {
            editData()
        }

        binding.btnClose.setOnClickListener() {
            dismiss()
        }
        binding.btnDelete.setOnClickListener()  {
            viewModel.deleteFrage(myFrage)
            dismiss()
        }

    }

    private fun setSpinner() {

        if (myFrage.korrekteAntwort == myFrage.antwortA) {
            binding.spinner.setSelection(0)
            originalSpinnerPosition = 0
        }
        if (myFrage.korrekteAntwort == myFrage.antwortB) {
            binding.spinner.setSelection(1)
            originalSpinnerPosition = 1
        }
        if (myFrage.korrekteAntwort == myFrage.antwortC) {
            binding.spinner.setSelection(2)
            originalSpinnerPosition = 2
        }
        if (myFrage.korrekteAntwort == myFrage.antwortD) {
            binding.spinner.setSelection(3)
            originalSpinnerPosition = 3
        }
    }


    private fun editData() {
        if (isDataValid()) {
            myFrage.frage = binding.etFrageMultipleChoice.editText?.text.toString().trim()
            myFrage.antwortA = binding.etAntwortA.editText?.text.toString().trim()
            myFrage.antwortB = binding.etAntwortB.editText?.text.toString().trim()
            myFrage.antwortC = binding.etAntwortC.editText?.text.toString().trim()
            myFrage.antwortD = binding.etAntwortD.editText?.text.toString().trim()

            var korrekteAntwort: String

            //Korrekte Antwort
            if (binding.spinner.selectedItemPosition == 0) {
                korrekteAntwort = binding.etAntwortA.editText?.text.toString().trim()
                myFrage.korrekteAntwort = korrekteAntwort

                Log.d("SaveFrage", "Hello World Save Frage: Spinnerpos = ${binding.spinner.selectedItemPosition} und SpinnerItem = ${binding.spinner.selectedItem} und korrekteAntwort: ${korrekteAntwort} und myFrage.Korrekteantwort: ${myFrage.korrekteAntwort}")
            }
            if (binding.spinner.selectedItemPosition == 1) {
                korrekteAntwort = binding.etAntwortB.editText?.text.toString().trim()
                myFrage.korrekteAntwort = korrekteAntwort

                Log.d("SaveFrage", "Hello World Save Frage: Spinnerpos = ${binding.spinner.selectedItemPosition} und SpinnerItem = ${binding.spinner.selectedItem} und korrekteAntwort: ${korrekteAntwort} und myFrage.Korrekteantwort: ${myFrage.korrekteAntwort}")

            }
            if (binding.spinner.selectedItemPosition == 2) {
                korrekteAntwort = binding.etAntwortC.editText?.text.toString().trim()
                myFrage.korrekteAntwort = korrekteAntwort

                Log.d("SaveFrage", "Hello World Save Frage: Spinnerpos = ${binding.spinner.selectedItemPosition} und SpinnerItem = ${binding.spinner.selectedItem} und korrekteAntwort: ${korrekteAntwort} und myFrage.Korrekteantwort: ${myFrage.korrekteAntwort}")

            }
            if (binding.spinner.selectedItemPosition == 3)
            {
                korrekteAntwort = binding.etAntwortD.editText?.text.toString().trim()
                myFrage.korrekteAntwort = korrekteAntwort

                Log.d("SaveFrage", "Hello World Save Frage: Spinnerpos = ${binding.spinner.selectedItemPosition} und SpinnerItem = ${binding.spinner.selectedItem} und korrekteAntwort: ${korrekteAntwort} und myFrage.Korrekteantwort: ${myFrage.korrekteAntwort}")

            }

            viewModel.updateFrage(myFrage)
            originalSpinnerPosition = binding.spinner.selectedItemPosition


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

    private fun isDataValid(): Boolean {
        val neueFrage = binding.etFrageMultipleChoice.editText?.text.toString().trim()
        val neueAntwortA = binding.etAntwortA.editText?.text.toString().trim()
        val neueAntwortB = binding.etAntwortB.editText?.text.toString().trim()
        val neueAntwortC = binding.etAntwortC.editText?.text.toString().trim()
        val neueAntwortD = binding.etAntwortD.editText?.text.toString().trim()
        val spinnerPosition = binding.spinner.selectedItemPosition

        // Überprüfen, ob die Frage geändert wurde und nicht null oder leer ist
        if (
            neueFrage.isNotEmpty()
            && neueAntwortA.isNotEmpty()
            && neueAntwortB.isNotEmpty()
            && neueAntwortC.isNotEmpty()
            && neueAntwortD.isNotEmpty()

            &&
            (neueFrage != myFrage.frage
                    || neueAntwortA != myFrage.antwortA
                    || neueAntwortB != myFrage.antwortB
                    || neueAntwortC != myFrage.antwortC
                    || neueAntwortD != myFrage.antwortD
                    || spinnerPosition != originalSpinnerPosition
                    )
        ) {
            // Daten sind gültig
            return true
        } else {
            // Daten wurden nicht geändert oder sind ungültig
            return false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}