package com.dhbw.progresstracker.repository

import android.app.Application
import android.util.Log
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
    private var liveDataFragen = repository.getLiveDataFragen()
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

    fun insertFrage(kategorieId: Int, question: String, antwortA: String?, antwortB: String?, antwortC: String?, antwortD: String?,fehlerAntwort: String?, korrekteAntwort: String, fragetyp: Fragetyp ) {
        viewModelScope.launch {
            val frage = Frage(kategorieId, question, antwortA, antwortB, antwortC, antwortD,fehlerAntwort, korrekteAntwort, fragetyp)
            repository.insertFrage(frage)
        }
    }

    fun deleteFrage(frage: Frage){
        viewModelScope.launch {
            repository.deleteFrage(frage)
        }
    }

    fun updateFrage(frage: Frage){
        viewModelScope.launch {
            repository.updateFrage(frage)
        }
    }

    fun getFrageByIdNoSuspend(frageId: Int): Frage? {
        var myFrage: Frage? = null

        myFrage = repository.getFrageByIdNoSuspend(frageId)

        return myFrage
    }

    private val filteredKategorieId = MutableLiveData<Int>()

    // LiveData to observe filtered Fragen
    val filteredFragen: LiveData<List<Frage>> = filteredKategorieId.switchMap { kategorieId ->
        repository.getLiveDataFragenForKategorie(kategorieId)
    }

    // ... Other code ...

    fun getLiveDataFragenByKategorie(kategorieId: Int) {
        filteredKategorieId.value = kategorieId
    }

    fun getFrageById(frageId: Int): Frage?{
        var frage: Frage? = null
        viewModelScope.launch {
            try {
                frage = repository.getFrageById(frageId)
                Log.d("ViewModel", "Frage geladen: ${frage?.frage}")

            } catch (e: Exception) {

                Log.e("ViewModel", "Fehler beim Laden der Frage", e)
            }
        }
        return frage
    }

    fun getLiveDataFragen(): LiveData<List<Frage>> = liveDataFragen



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