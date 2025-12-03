package com.example.bustrackerfvg.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Questa annotazione dice a Room che questa classe è una tabella del database
@Entity(tableName = "bus_stop")
data class BusStop(
    // ID autogenerato: non dobbiamo preoccuparcene noi
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Il nome che diamo alla fermata (es. "Stazione FS")
    val name: String,

    // Il codice estratto dall'URL (es. "UD237")
    val code: String
)