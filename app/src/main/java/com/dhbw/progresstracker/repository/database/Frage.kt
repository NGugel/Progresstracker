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

    val kategorieId: Int,
    var frage: String,
    var antwortA: String? = null,  // Hier könnten die Antwortmöglichkeiten für Multiple Choice sein
    var antwortB: String? = null,
    var antwortC: String? = null,
    var antwortD: String? = null,
    var fehlerAntwort: String? = null,
    var korrekteAntwort: String,  // Hier könnte die korrekte Antwort für andere Typen sein
    val fragetyp: Fragetyp,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)

enum class Fragetyp {
    MULTIPLE_CHOICE,
    FREITEXT,
    FEHLERTEXT
    // Weitere Fragetypen hier hinzufügen, falls erforderlich
}