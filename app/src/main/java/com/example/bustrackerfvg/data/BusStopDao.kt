package com.example.bustrackerfvg.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BusStopDao {
    // Restituisce tutte le fermate. "Flow" significa che se i dati cambiano,
    // la lista si aggiorna da sola in tempo reale.
    @Query("SELECT * FROM bus_stop ORDER BY name ASC")
    fun getAllStops(): Flow<List<BusStop>>

    // Inserisce una nuova fermata. Se esiste già, la sostituisce (o la ignora).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStop(stop: BusStop)

    // Inserisce una lista di fermate (per il primo avvio)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stops: List<BusStop>)

    // Cancella una fermata specifica
    @Delete
    suspend fun deleteStop(stop: BusStop)
}