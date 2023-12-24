package com.dhbw.progresstracker.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dhbw.progresstracker.repository.database.Frage
import com.dhbw.progresstracker.repository.database.FrageDao
import com.dhbw.progresstracker.repository.database.Kategorie
import com.dhbw.progresstracker.repository.database.KategorieDao
import com.dhbw.progresstracker.repository.database.LocalDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(application: Application, private val scope: CoroutineScope)
{
    private val kategorieDao:KategorieDao
    private val frageDao:FrageDao

    init {
        val db = LocalDatabase.createInstance(application, scope)
        kategorieDao  = db.kategorieDao
        frageDao = db.frageDao
    }

    // implement all Methods:
    suspend fun insertKategorie(kategorie: Kategorie)
    {
        withContext(Dispatchers.IO)
        {
            kategorieDao.insertKategorie(kategorie)
        }
    }

    suspend fun updateKategorie(kategorie: Kategorie)
    {
        withContext(Dispatchers.IO)
        {
            kategorieDao.updateKategorie(kategorie)
        }
    }

    suspend fun deleteKategorie(kategorie: Kategorie)
    {
        withContext(Dispatchers.IO)
        {
            kategorieDao.deleteKategorie(kategorie)
        }
    }

    fun getLiveDataKategorien(): LiveData<List<Kategorie>> =  kategorieDao.getLiveDataKategorienList()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Frage
    ///////////////////////////////////////////////////////////////////////////////////////////

    suspend fun insertFrage(frage: Frage)
    {
        withContext(Dispatchers.IO)
        {
            frageDao.insertFrage(frage)
        }
    }

    suspend fun updateFrage(frage: Frage)
    {
        withContext(Dispatchers.IO)
        {
            frageDao.updateFrage(frage)
        }
    }

    suspend fun deleteFrage(frage: Frage)
    {
        withContext(Dispatchers.IO)
        {
            frageDao.deleteFrage(frage)
        }
    }

    fun getLiveDataFragen(): LiveData<List<Frage>> = frageDao.getLiveDataFragenList()


    fun getLiveDataFragenForKategorie(kategorieId: Int): LiveData<List<Frage>> =  frageDao.getLiveDataFragenForKategorie(kategorieId)

}