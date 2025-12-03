package com.example.bustrackerfvg

import android.app.Application
import com.example.bustrackerfvg.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BusApplication : Application() {
    // Questo scope serve a mantenere vivo il database
    val applicationScope = CoroutineScope(SupervisorJob())

    // Creiamo il database una volta sola quando l'app si avvia
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
}