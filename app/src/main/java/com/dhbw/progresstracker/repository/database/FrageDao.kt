package com.dhbw.progresstracker.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface FrageDao {

    @Insert
    suspend fun insertFrage(frage: Frage)

    @Update
    suspend fun updateFrage(frage: Frage)

    @Delete
    suspend fun deleteFrage(frage: Frage)

    @Query("SELECT * FROM Frage")
    fun getLiveDataFragenList():LiveData<List<Frage>>

    @Query("SELECT * FROM Frage WHERE kategorieId = :kategorieId")
    fun getLiveDataFragenForKategorie(kategorieId: Int): LiveData<List<Frage>>

}
