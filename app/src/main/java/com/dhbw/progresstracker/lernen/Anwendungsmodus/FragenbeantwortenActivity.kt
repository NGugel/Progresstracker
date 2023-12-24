package com.dhbw.progresstracker.lernen.Anwendungsmodus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhbw.progresstracker.R
import com.dhbw.progresstracker.databinding.ActivityFragebeantwortenBinding
import com.dhbw.progresstracker.repository.ViewModel
import com.dhbw.progresstracker.repository.database.Frage
import com.dhbw.progresstracker.repository.database.Fragetyp

class FragenbeantwortenActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    var binding: ActivityFragebeantwortenBinding? = null
    private lateinit var viewModel: ViewModel
    var empfangeneKategorieId = 0
    var empfangeneFragenanzahl = 0
    var fragenCounter = 0
    var fragenSet: MutableList<Frage>? = null
    var fragenLeft: Int? = null

    var currentFragment: Fragment? = null


    private val myFragmentLifecycleCallbacks =
        object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentDestroyed(fm, f)

                // Auf die Zerstörung des Fragments reagieren
                Log.d("FragenbeantwortenActivity", "Fragment wurde zerstört")
                zeigeFragenInSchleife()

            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        empfangeneKategorieId = intent.getIntExtra("KATEGORIE_KEY", 0)
        empfangeneFragenanzahl = intent.getIntExtra("FRAGENANZAHL", 0)

        fragenLeft = empfangeneFragenanzahl


        Log.d(
            "Fragenbeantworten",
            "Hello World von Fragenbeantwroten empfangeneKategorieId: ${empfangeneKategorieId}"
        )

        binding = ActivityFragebeantwortenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //ViewModel initialisieren
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        //Werte setzen
        binding?.tvFragencount?.text = "Frage ${fragenCounter} von ${empfangeneFragenanzahl}"


        //Alle Kategorien oder nur eine ausgewähl?
        if (empfangeneKategorieId == 0) { // Alle Kategorien
            viewModel.getLiveDataFragen().observe(this, Observer { fragen ->
                fragenSet = fragen.toMutableList()
                Log.d("FrageanzahlDialog", "Hello World von FragenLiveDataObserver")

                // Hier kannst du die Schleife für das Anzeigen der Fragen hinzufügen
                zeigeFragenInSchleife()

            })
        } else {
            viewModel.filteredFragen.observe(this, Observer { fragen ->
                fragenSet = fragen.toMutableList()
                Log.d("FrageanzahlDialog", "Hello World von FragenLiveDataObserver")

                // Hier kannst du die Schleife für das Anzeigen der Fragen hinzufügen
                zeigeFragenInSchleife()

            })

            viewModel.getLiveDataFragenByKategorie(empfangeneKategorieId)
        }


        //Zurück
        val zurueckbutton = findViewById<ImageButton>(R.id.zurueckbutton)

        zurueckbutton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(
            myFragmentLifecycleCallbacks,
            false
        )

    }

    fun <E> List<E>.randomOrNull(): E? = if (size > 0) random() else null

    private fun showFragment(fragment: Fragment) {

        currentFragment = fragment

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun zeigeFragenInSchleife() {
        val aktuelleFrage = fragenSet?.randomOrNull()

        if (fragenLeft == 0) {
            Toast.makeText(
                this,
                "Super, du bist mit deinen vorgenommenen Fragen durch.", Toast.LENGTH_LONG
            ).show()
            startActivity(Intent(this, KategorieAuswahlActivity::class.java))

        } else {
            if (aktuelleFrage != null) {
                // Hier je nach Fragetyp das entsprechende Fragment erstellen und anzeigen
                when (aktuelleFrage.fragetyp) {
                    Fragetyp.FREITEXT -> {
                        val freitextbeantwortenFragment = FreitextbeantwortenFragment(aktuelleFrage)
                        showFragment(freitextbeantwortenFragment)
                        Log.d(
                            "Fragebeantworten",
                            "Hello World von FragebeantwortenActivity, Fragment Freitext, Aktuelle Frage: ${aktuelleFrage.frage}"
                        )
                    }

                    Fragetyp.FEHLERTEXT -> {
                        val fehlerTextbeantwortenFragment =
                            FehlertextbeantwortenFragment(aktuelleFrage)
                        showFragment(fehlerTextbeantwortenFragment)
                        Log.d(
                            "Fragebeantworten",
                            "Hello World von FragebeantwortenActivity, Fragment Fehlertext, Aktuelle Frage: ${aktuelleFrage.frage}"
                        )
                    }

                    Fragetyp.MULTIPLE_CHOICE -> {
                        val multipleChoicebeantwortenFragment =
                            MultipleChoicebeantwortenFragment(aktuelleFrage)
                        showFragment(multipleChoicebeantwortenFragment)
                        Log.d(
                            "Fragebeantworten",
                            "Hello World von FragebeantwortenActivity, Fragment Freitext, Aktuelle Frage: ${aktuelleFrage.frage}"
                        )
                    }
                }

                fragenSet?.remove(aktuelleFrage)
                fragenCounter = fragenCounter + 1
                fragenLeft = fragenLeft!! - 1
                binding?.tvFragencount?.text =
                    "Frage ${fragenCounter} von ${empfangeneFragenanzahl}"

            }
        }
    }

    override fun onDestroy() {
        // FragmentLifecycleCallbacks entfernen
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(myFragmentLifecycleCallbacks)
        super.onDestroy()
    }

}