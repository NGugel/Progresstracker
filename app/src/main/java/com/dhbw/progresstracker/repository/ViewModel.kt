package com.dhbw.progresstracker.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhbw.progresstracker.repository.AppRepository
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


}