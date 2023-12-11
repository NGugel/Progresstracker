package com.dhbw.progresstracker.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dhbw.progresstracker.repository.database.Kategorie
import com.dhbw.progresstracker.repository.database.KategorieDao
import com.dhbw.progresstracker.repository.database.LocalDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(application: Application, private val scope: CoroutineScope)
{
    private val kategorieDao:KategorieDao

    init {
        val db = LocalDatabase.createInstance(application, scope)
        kategorieDao  = db.kategorieDao
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

    suspend fun getKategorieById(kategorieId:Int):Kategorie?
    {
        var kategorie:Kategorie? = null
        withContext(Dispatchers.IO)
        {
            kategorie = kategorieDao.getKategorieById(kategorieId)
        }

        return kategorie
    }

    suspend fun getAllKategorien():List<Kategorie>?
    {
        var kategorien:List<Kategorie>?  = null
        withContext(Dispatchers.IO)
        {
            kategorien = kategorieDao.getKategorienList()
        }
        return kategorien
    }

    fun getLiveDataKategorien(): LiveData<List<Kategorie>> =  kategorieDao.getLiveDataKategorienList()
}