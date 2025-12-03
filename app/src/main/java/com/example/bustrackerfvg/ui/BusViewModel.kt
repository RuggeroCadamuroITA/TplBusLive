package com.example.bustrackerfvg.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bustrackerfvg.data.BusStop
import com.example.bustrackerfvg.data.BusStopDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// Il ViewModel gestisce i dati per la schermata
class BusViewModel(private val dao: BusStopDao) : ViewModel() {

    // Questa lista si aggiorna automaticamente se il database cambia
    val allStops: Flow<List<BusStop>> = dao.getAllStops()

    // Funzione per aggiungere una fermata
    fun addStop(name: String, code: String) {
        // Lanciamo un'operazione in background (coroutine)
        viewModelScope.launch {
            val newStop = BusStop(name = name, code = code)
            dao.insertStop(newStop)
        }
    }

    // Funzione per cancellare una fermata
    fun deleteStop(stop: BusStop) {
        viewModelScope.launch {
            dao.deleteStop(stop)
        }
    }
}

// Questa classe serve a creare il ViewModel passandogli il DAO (Factory)
class BusViewModelFactory(private val dao: BusStopDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}