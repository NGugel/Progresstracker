package com.dhbw.progresstracker.repository.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Kategorie(

    val titel: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Parcelable
