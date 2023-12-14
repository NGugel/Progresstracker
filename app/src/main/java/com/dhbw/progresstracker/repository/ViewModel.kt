package com.dhbw.progresstracker.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.dhbw.progresstracker.repository.AppRepository
import com.dhbw.progresstracker.repository.database.Frage
import com.dhbw.progresstracker.repository.database.Fragetyp
import com.dhbw.progresstracker.repository.database.Kategorie
import kotlinx.coroutines.launch


class ViewModel(application: Application) : AndroidViewModel(application) {

    //////////////////////////////////////////////////////////////////
    // Repository
    private val repository = AppRepository(application, viewModelScope)
    private var liveDataKategorien = repository.getLiveDataKategorien()
    ////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////
    // Methods to interact with the repository:

    fun insert(titel: String) {
        viewModelScope.launch {
            val kategorie = Kategorie(titel)
            repository.insertKategorie(kategorie)
        }
    }

    fun update(kategorie: Kategorie) {
        viewModelScope.launch {
            repository.updateKategorie(kategorie)
        }
    }

    fun delete(kategorie: Kategorie) {
        viewModelScope.launch {
            repository.deleteKategorie(kategorie)
        }
    }

    fun getKategorieById(kategorieId: Int): Kategorie? {
        var kategorie: Kategorie? = null
        viewModelScope.launch {
            kategorie = repository.getKategorieById(kategorieId)
        }
        return kategorie
    }

    fun getAllKategorien(): List<Kategorie>? {
        var kategorien: List<Kategorie>? = null
        viewModelScope.launch {
            kategorien = repository.getAllKategorien()
        }
        return kategorien
    }
    ///////////////////////////////////////////


    //////////////////////////////////////////////////////
    // get Methode for LiveData
    fun getLiveDataKategorien(): LiveData<List<Kategorie>> = liveDataKategorien
    ////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////
    //Frage
    ////////////////////////////////////////////////////////

    fun insertFrage(kategorieId: Int, question: String, antwortA: String?, antwortB: String?, antwortC: String?, antwortD: String?, korrekteAntwort: String, fragetyp: Fragetyp ) {
        viewModelScope.launch {
            val frage = Frage(kategorieId, question, antwortA, antwortB, antwortC, antwortD, korrekteAntwort, fragetyp)
            repository.insertFrage(frage)
        }



    }

    private val filteredKategorieId = MutableLiveData<Int>()

    // LiveData to observe filtered Fragen
    val filteredFragen: LiveData<List<Frage>> = filteredKategorieId.switchMap { kategorieId ->
        repository.getLiveDataFragen(kategorieId)
    }

    // ... Other code ...

    fun getLiveDataFragenByKategorie(kategorieId: Int) {
        filteredKategorieId.value = kategorieId
    }


  /* val id: Int = 0,
    val kategorieId: Int,
    val frage: String,
    val antwortA: String? = null,  // Hier könnten die Antwortmöglichkeiten für Multiple Choice sein
    val antwortB: String? = null,
    val antwortC: String? = null,
    val antwortD: String? = null,
    val korrekteAntwort: String? = null,  // Hier könnte die korrekte Antwort für andere Typen sein
    val fragetyp: Fragetyp
*/

}