package com.dhbw.progresstracker.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
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

    private val filteredKategorieId = MutableLiveData<Int>()

    // LiveData to observe filtered Fragen
    val filteredFragen: LiveData<List<Frage>> = filteredKategorieId.switchMap { kategorieId ->
        repository.getLiveDataFragenForKategorie(kategorieId)
    }

    fun getLiveDataFragenByKategorie(kategorieId: Int) {
        filteredKategorieId.value = kategorieId
    }

    fun getLiveDataFragen(): LiveData<List<Frage>> = liveDataFragen

}