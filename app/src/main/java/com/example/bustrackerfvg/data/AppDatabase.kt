package com.example.bustrackerfvg.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Definiamo il database e le tabelle che contiene
@Database(entities = [BusStop::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun busStopDao(): BusStopDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bus_tracker_database"
                )
                    // Aggiungiamo la callback per popolare il DB quando viene creato
                    .addCallback(BusStopDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // Questa classe serve a riempire il database la prima volta che apri l'app
    private class BusStopDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.busStopDao())
                }
            }
        }

        suspend fun populateDatabase(busStopDao: BusStopDao) {
            // Qui inseriamo i dati forniti nel piano iniziale
            val initialStops = listOf(
                BusStop(name = "Fronte Malignani", code = "70C72"),
                BusStop(name = "Piazza Chiesa", code = "UD043"),
                BusStop(name = "Zanon", code = "70C80"),
                BusStop(name = "Stazione FS", code = "UD237"),
                BusStop(name = "Nogaredo 47", code = "UD016"),
                BusStop(name = "Fronte Stazione FS", code = "70C37"),
                BusStop(name = "Tribunale", code = "UD345"),
                BusStop(name = "Malignani", code = "70C72")
            )
            busStopDao.insertAll(initialStops)
        }
    }
}