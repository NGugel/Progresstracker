import android.os.Bundle
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
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.ViewModelFactory

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMultiplechoiceBinding.inflate(inflater, container, false)

        // Initialisiere die Views
      //  questionEditText = binding.textInputLayout.editText
       // answerAEditText = view.findViewById(R.id.etAntwortA.editText)
        //answerBEditText = view.findViewById(R.id.etAntwortB.editText)
        //answerCEditText = view.findViewById(R.id.etAntwortC.editText)
        //answerDEditText = view.findViewById(R.id.etAntwortD)
        //spinner = view.findViewById(R.id.spinner)
        btnSave = binding.btnSpeichern
        btnCancel = binding.btnCancel

        // Setze Spinner-Adapter mit den Antwortmöglichkeiten
        val answers = resources.getStringArray(R.array.antworten)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, answers)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
       // spinner.adapter = adapter

        // Setze Click-Listener für die Buttons
        btnSave.setOnClickListener {
            saveData()
        }

        btnCancel.setOnClickListener {
            // Füge hier die Aktion für den Abbrechen-Button ein
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireActivity().application)
        ).get(ViewModel::class.java)



        binding.btnCancel.setOnClickListener {
            // Aktion bei Klick auf den Abbrechen-Button
            //dismiss() // Schließe den Dialog
        }
    }

    private fun saveData() {
        // Validiere die Eingaben und führe entsprechende Aktionen durch
        val question = questionEditText.text.toString()
        val answerA = answerAEditText.text.toString()
        val answerB = answerBEditText.text.toString()
        val answerC = answerCEditText.text.toString()
        val answerD = answerDEditText.text.toString()
        val correctAnswer = spinner.selectedItem.toString()

        // Füge hier die Logik zum Speichern der Daten ein

        // Beispiel: Zeige eine Toast-Nachricht mit den eingegebenen Daten an
        val message = "Frage: $question\n" +
                "Antwort A: $answerA\n" +
                "Antwort B: $answerB\n" +
                "Antwort C: $answerC\n" +
                "Antwort D: $answerD\n" +
                "Richtige Antwort: $correctAnswer"

        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}