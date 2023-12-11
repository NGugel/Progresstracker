package com.dhbw.progresstracker.lernen.Verwaltung.Kategorie

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.databinding.DialogKategorieinputBinding
import com.google.android.material.textfield.TextInputLayout
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.DialogFrageinputBinding
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.ViewModelFactory

class FrageDialogInput : DialogFragment() {
    private lateinit var rootView: View

    // Views:
    private lateinit var btnSpeichern: Button
    private lateinit var btnAbbrechen: Button

    private lateinit var etKategorie: TextInputLayout

    // ViewModel
    private lateinit var viewModel: ViewModel

    private var _binding: DialogFrageinputBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog)



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)

        _binding = DialogFrageinputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireActivity().application)
        ).get(ViewModel::class.java)

        binding.dialogBtnSpeichern.setOnClickListener {
            // Aktion bei Klick auf den Speichern-Button
           // saveData()
            // Hier kannst du den Benutzereingabe-Wert verwenden
        }

        binding.dialogBtnAbbrechen.setOnClickListener {
            // Aktion bei Klick auf den Abbrechen-Button
            dismiss() // Schließe den Dialog
        }
    }
/*
    private fun saveData() {
        if (!TextUtils.isEmpty(binding.dialogEtKategorie.editText?.text.toString())) {
            viewModel.insert(binding.dialogEtKategorie.editText?.text.toString())
            Toast.makeText(
                requireContext(),
                "Kategorie wurde erfolgreich angelegt",
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        } else {
            Toast.makeText(requireContext(), "Bitte zuerst Kategorie eingeben", Toast.LENGTH_SHORT)
                .show()
        }
    }

 */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}