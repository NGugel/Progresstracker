package com.dhbw.progresstracker.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Frage(
    val titel: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
