package com.example.bustrackerfvg.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bustrackerfvg.data.BusStop

// 1. Questa è la funzione principale che disegna tutta la pagina
@Composable
fun HomeScreen(
    busStopList: List<BusStop>,       // La lista delle fermate da mostrare
    onStopClick: (BusStop) -> Unit,   // Cosa succede se clicchi una fermata
    onDeleteClick: (BusStop) -> Unit, // Cosa succede se clicchi il cestino
    onAddClick: () -> Unit            // Cosa succede se clicchi il tasto +
) {
    Scaffold(
        // Il tasto "+" galleggiante in basso a destra
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Aggiungi Fermata")
            }
        }
    ) { paddingValues ->
        // La lista vera e propria
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Spazio tra le card
        ) {
            items(busStopList) { stop ->
                BusStopItem(
                    stop = stop,
                    onClick = { onStopClick(stop) },
                    onDelete = { onDeleteClick(stop) }
                )
            }
        }
    }
}

// 2. Questa funzione disegna la singola riga (la "Card")
@Composable
fun BusStopItem(
    stop: BusStop,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Rende tutta la card cliccabile
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icona del Bus a sinistra
            Icon(
                imageVector = Icons.Default.DirectionsBus,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Colonna centrale con Nome e Codice
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stop.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Codice: ${stop.code}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // Tasto Cestino a destra
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Elimina",
                    tint = Color.Red
                )
            }
        }
    }
}