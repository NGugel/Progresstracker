package com.dhbw.progresstracker.lernen.Verwaltung.Kategorie

import MultipleChoiceFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.DialogFrageinputBinding
import com.dhbw.progresstracker.lernen.Verwaltung.Frage.FehlertextFragment
import com.dhbw.progresstracker.lernen.Verwaltung.Frage.FreitextFragment
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.ViewModelFactory

class FrageDialogInput : DialogFragment() {

    // ViewModel
    private lateinit var viewModel: ViewModel

    private var _binding: DialogFrageinputBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val EXTRA_KATEGORIE = "extra_kategorie"
    }

    var empfangeneKategorieId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)

        arguments?.let {
            empfangeneKategorieId = it.getInt(EXTRA_KATEGORIE)

            Log.d("FrageDialogInput", "Hello World von FrageDialgInput die übergebene KategorieId heißt:  ${empfangeneKategorieId}!")
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
                        Log.d("FrageDialogInput", "Hello World von showFragment, Spinner 0 Selected! == MultipleChoice")


                    }
                    1 -> {
                        val freitextFragment = FreitextFragment()

                        val bundle = Bundle()
                        bundle.putInt(EXTRA_KATEGORIE, empfangeneKategorieId)
                        freitextFragment.arguments = bundle

                        showFragment(freitextFragment)
                        Log.d("FrageDialogInput", "Hello World von showFragment,Spinner 1 Selected! == Freitext")
                    }
                    2 -> {
                        val fehlertextFragment = FehlertextFragment()

                        val bundle = Bundle()
                        bundle.putInt(EXTRA_KATEGORIE, empfangeneKategorieId)
                        fehlertextFragment.arguments = bundle

                        showFragment(fehlertextFragment)
                        Log.d("VerwaltenActivity", "Hello World von showFragment,Spinner 2 Selected! == Fehlertext")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}