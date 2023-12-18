package com.dhbw.progresstracker.lernen.Verwaltung.Kategorie

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.DialogKategorieeditBinding
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.ViewModelFactory
import com.dhbw.progresstracker.repository.database.Kategorie

class KategorieDialogEdit : DialogFragment() {


    // ViewModel
    private lateinit var viewModel: ViewModel

    private var _binding: DialogKategorieeditBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val EXTRA_KATEGORIE = "extra_kategorie"
        const val EXTRA_KATEGORIETITEL = "extra_kategorietitel"
    }

    var empfangeneKategorieId: Int = 0
    var empfangeneKategorieTitel: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)

        arguments?.let {
            empfangeneKategorieId = it.getInt(EXTRA_KATEGORIE)
            empfangeneKategorieTitel = it.getString(EXTRA_KATEGORIETITEL).toString()

            Log.d("KategorieDialogEdit", "Hello World von KategorieDialogEdit die übergebene KategorieId heißt:  ${empfangeneKategorieId}!")
            // Jetzt kannst du empfangeneKategorie in diesem Fragment verwenden
            // ...
        }

        _binding = DialogKategorieeditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireActivity().application)
        ).get(ViewModel::class.java)

        // Hier kannst du auf die Views im Binding-Objekt zugreifen
        // Beispiel: binding.textViewTitle.text = "Dein Titel"

        binding.tvTitle.text = "Kategorie '${empfangeneKategorieTitel}' umbenennen"
        binding.dialogEtKategorie.editText?.setText(empfangeneKategorieTitel)


        Log.d("KategorieDialogEdit", "Hello World von KategorieDialogEdit die geholte Kategorie heißt ${empfangeneKategorieTitel}")

        Toast.makeText(requireContext(), "Ausgewählte Kategorie: ${empfangeneKategorieTitel}.", Toast.LENGTH_SHORT)


        binding.dialogBtnSpeichern.setOnClickListener {
            // Aktion bei Klick auf den Speichern-Button
            editData()
            // Hier kannst du den Benutzereingabe-Wert verwenden
        }

        binding.dialogBtnAbbrechen.setOnClickListener {
            // Aktion bei Klick auf den Abbrechen-Button
            dismiss() // Schließe den Dialog
        }
    }

    private fun editData() {
        val neueBezeichnung = binding.dialogEtKategorie.editText?.text.toString().trim()

        if (neueBezeichnung.isNotEmpty() && neueBezeichnung != empfangeneKategorieTitel) {
            viewModel.update(Kategorie(neueBezeichnung, empfangeneKategorieId))
            Toast.makeText(
                requireContext(),
                "Kategorie wurde erfolgreich umbenannt",
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        } else {
            Toast.makeText(
                requireContext(),
                "Feld darf nicht leer sein und muss geändert sein.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}