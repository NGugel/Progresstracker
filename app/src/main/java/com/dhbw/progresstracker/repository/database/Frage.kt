package com.dhbw.progresstracker.repository.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Kategorie::class,
            parentColumns = ["id"],
            childColumns = ["kategorieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Frage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val kategorieId: Int,
    val text: String,
    val antwortA: String? = null,  // Hier könnten die Antwortmöglichkeiten für Multiple Choice sein
    val antwortB: String? = null,
    val antwortC: String? = null,
    val korrekteAntwort: String? = null,  // Hier könnte die korrekte Antwort für andere Typen sein
    val fragetyp: Fragetyp
)

enum class Fragetyp {
    MULTIPLE_CHOICE,
    FREITEXT,
    FEHLER_KORRIGIEREN,
    LUECKENTEXT,
    // Weitere Fragetypen hier hinzufügen, falls erforderlich
}