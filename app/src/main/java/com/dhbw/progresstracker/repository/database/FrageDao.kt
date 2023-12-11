package com.dhbw.progresstracker.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface FrageDao {

    @Insert
    suspend fun insertFrage(Frage: Frage)

    @Delete
    suspend fun deleteFrage(Frage: Frage)
}