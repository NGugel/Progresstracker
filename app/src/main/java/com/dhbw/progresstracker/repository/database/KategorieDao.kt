package com.dhbw.progresstracker.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface KategorieDao {


    //Suspend vor funktion lässt diese als Koroutine ausführen
    @Insert
    suspend fun insertKategorie(Kategorie: Kategorie)

    @Delete
    suspend fun deleteKategorie(Kategorie: Kategorie)

    @Update
    suspend fun updateKategorie(Kategorie: Kategorie)

    @Query("SELECT * FROM Kategorie WHERE id = :kategorieId")
    suspend fun getKategorieById(kategorieId:Int): Kategorie

    @Query("SELECT * FROM Kategorie")
    suspend fun getKategorienList(): List<Kategorie>

    @Query("SELECT * FROM Kategorie")
    fun getLiveDataKategorienList():LiveData<List<Kategorie>>

    @Query("SELECT * FROM Kategorie WHERE id = :kategorieId")
    fun getLiveDataKategorieById(kategorieId: Int): LiveData<Kategorie?>
}