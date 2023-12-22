import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.DialogFrageinputBinding
import com.dhbw.progresstracker.databinding.FragmentMultiplechoiceBinding
import com.dhbw.progresstracker.lernen.Verwaltung.Kategorie.FrageDialogInput
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.ViewModelFactory
import com.dhbw.progresstracker.repository.database.Frage
import com.dhbw.progresstracker.repository.database.Fragetyp
import com.dhbw.progresstracker.repository.database.Kategorie

class MultipleChoiceFragment : Fragment() {

    private lateinit var questionEditText: TextInputEditText
    private lateinit var answerAEditText: TextInputEditText
    private lateinit var answerBEditText: TextInputEditText
    private lateinit var answerCEditText: TextInputEditText
    private lateinit var answerDEditText: TextInputEditText
    private lateinit var spinner: Spinner
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private var _binding: FragmentMultiplechoiceBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ViewModel

    companion object {
        const val EXTRA_KATEGORIE = "extra_kategorie"
    }

    var empfangeneKategorieId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        arguments?.let {
            empfangeneKategorieId = it.getInt(EXTRA_KATEGORIE)

            Log.d("MultipleChooiceFragment", "Hello World von MultipleChoiceFragment die übergebene KategorieId heißt:  ${empfangeneKategorieId}!")
            // Jetzt kannst du empfangeneKategorie in diesem Fragment verwenden
            // ...
        }

        _binding = FragmentMultiplechoiceBinding.inflate(inflater, container, false)

        // Initialisiere die Views
      //  questionEditText = binding.textInputLayout.editText
       // answerAEditText = view.findViewById(R.id.etAntwortA.editText)
        //answerBEditText = view.findViewById(R.id.etAntwortB.editText)
        //answerCEditText = view.findViewById(R.id.etAntwortC.editText)
        //answerDEditText = view.findViewById(R.id.etAntwortD)
        //spinner = view.findViewById(R.id.spinner)
        btnSave = binding.btnSpeichern

        // Setze Spinner-Adapter mit den Antwortmöglichkeiten
        val answers = resources.getStringArray(R.array.antworten)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, answers)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
       // spinner.adapter = adapter

        // Setze Click-Listener für die Buttons


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireActivity().application)
        ).get(ViewModel::class.java)

        btnSave.setOnClickListener {
            saveData()
        }

    }

    private fun saveData() {
        val frage = binding.etFrageMultipleChoice.editText?.text.toString()
        val antwortA = binding.etAntwortA.editText?.text.toString()
        val antwortB = binding.etAntwortB.editText?.text.toString()
        val antwortC = binding.etAntwortC.editText?.text.toString()
        val antwortD = binding.etAntwortD.editText?.text.toString()
        var korrekteAntwort = ""

        //Korrekte Antwort
        if(binding.spinner.selectedItemPosition == 0)
        {
            korrekteAntwort = antwortA
            Log.d("SaveFrage", "Hello World Save Frage: Spinnerpos = ${binding.spinner.selectedItemPosition} und SpinnerItem = ${binding.spinner.selectedItem}")
        }

        if(binding.spinner.selectedItemPosition == 1)
        {
            korrekteAntwort = antwortB
            Log.d("SaveFrage", "Hello World Save Frage: Spinnerpos = ${binding.spinner.selectedItemPosition} und SpinnerItem = ${binding.spinner.selectedItem}")
        }

        if(binding.spinner.selectedItemPosition == 2)
        {
            korrekteAntwort = antwortC
            Log.d("SaveFrage", "Hello World Save Frage: Spinnerpos = ${binding.spinner.selectedItemPosition} und SpinnerItem = ${binding.spinner.selectedItem}")

        }

        if(binding.spinner.selectedItemPosition == 3)
        {
            korrekteAntwort = antwortD
            Log.d("SaveFrage", "Hello World Save Frage: Spinnerpos = ${binding.spinner.selectedItemPosition} und SpinnerItem = ${binding.spinner.selectedItem}")
        }


        if (frage.isEmpty() || antwortA.isEmpty() || antwortB.isEmpty() || antwortC.isEmpty() || antwortD.isEmpty()) {
            // Zeige Toast-Meldung für leere Felder
            Toast.makeText(requireContext(), "Bitte fülle alle Felder aus", Toast.LENGTH_SHORT).show()
        } else {
            // Hier kannst du die nicht-leeren Eingaben verwenden und speichern
            viewModel.insertFrage(empfangeneKategorieId, frage, antwortA, antwortB, antwortC, antwortD,null, korrekteAntwort, Fragetyp.MULTIPLE_CHOICE)
            Toast.makeText(requireContext(), "Frage wurde erfolgreich gespeichert", Toast.LENGTH_SHORT).show()
            Log.d("SaveFrage", "Hello World Save Frage: KategorieID ${empfangeneKategorieId} und MeineFrage: ${frage} AntwortA: ${antwortA} AntwortB: ${antwortB} AntwortC: ${antwortC} AntwortD: ${antwortD} Korrekte Antwort: ${korrekteAntwort} und Fragetyp: ${Fragetyp.MULTIPLE_CHOICE}")
        }
    }
}