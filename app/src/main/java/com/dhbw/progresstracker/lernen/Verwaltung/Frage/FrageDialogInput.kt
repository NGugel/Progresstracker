package com.dhbw.progresstracker.lernen.Verwaltung.Kategorie

import MultipleChoiceFragment
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.databinding.DialogKategorieinputBinding
import com.google.android.material.textfield.TextInputLayout
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.DialogFrageinputBinding
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.ViewModelFactory
import com.dhbw.progresstracker.repository.database.Kategorie

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

    companion object {
        const val EXTRA_KATEGORIE = "extra_kategorie"
    }

    var empfangeneKategorieId: Int = 0


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

        arguments?.let {
            empfangeneKategorieId = it.getInt(EXTRA_KATEGORIE)

            Log.d("FrageDialogInput", "Hello World von FrageDialgInput die übergebene KategorieId heißt:  ${empfangeneKategorieId}!")
            // Jetzt kannst du empfangeneKategorie in diesem Fragment verwenden
            // ...
        }

        _binding = DialogFrageinputBinding.inflate(inflater, container, false)

        val spinnerFrageTyp: Spinner = binding.spinnerFrageTyp

        spinnerFrageTyp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Basierend auf der ausgewählten Option die notwendigen Felder hinzufügen
                when (position) {
                    0 -> {
                        val multipleChoiceFragment = MultipleChoiceFragment()


                        val bundle = Bundle()
                        bundle.putInt(EXTRA_KATEGORIE, empfangeneKategorieId)
                        multipleChoiceFragment.arguments = bundle

                        showFragment(multipleChoiceFragment)
                        Log.d("VerwaltenActivity", "Hello World von showFragment aka Spinner 0 Selected!")


                    }
                    1 -> {
                        // Zeige Felder für Frei formulierbare Antwort an
                        // Beispiel: Dynamisch Views hinzufügen
                        Log.d("VerwaltenActivity", "Hello World von showFragment aka Spinner 1 Selected!")
                    }
                    // Weitere Cases für andere Fragetypen hinzufügen
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle when nothing is selected
            }
        }


        return binding.root


    }

    private fun showFragment(fragment: Fragment) {

        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireActivity().application)
        ).get(ViewModel::class.java)

        binding.btnClose.setOnClickListener() {
            dismiss()
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