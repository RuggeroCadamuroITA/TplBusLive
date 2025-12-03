package com.example.bustrackerfvg

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bustrackerfvg.ui.AddStopScreen
import com.example.bustrackerfvg.ui.BusViewModel
import com.example.bustrackerfvg.ui.BusViewModelFactory
import com.example.bustrackerfvg.ui.DetailScreen
import com.example.bustrackerfvg.ui.HomeScreen
import com.example.bustrackerfvg.ui.theme.BusTrackerFVGTheme // Assicurati che questo import sia corretto

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusTrackerFVGTheme {
                // Contenitore principale dell'app
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusTrackerApp()
                }
            }
        }
    }
}

@Composable
fun BusTrackerApp() {
    // 1. SETUP DEL DATABASE E VIEWMODEL
    val context = LocalContext.current
    val application = context.applicationContext as BusApplication
    val database = application.database

    // Creiamo il ViewModel collegandolo al Database
    val viewModel: BusViewModel = viewModel(
        factory = BusViewModelFactory(database.busStopDao())
    )

    // 2. RACCOGLIAMO I DATI (Lista Fermate)
    // "collectAsState" fa sì che la lista si aggiorni da sola
    val busStopList by viewModel.allStops.collectAsState(initial = emptyList())

    // 3. GESTIONE NAVIGAZIONE
    val navController = rememberNavController()

    // 4. GESTIONE PERMESSI FOTOCAMERA
    // Questo serve a chiedere il permesso all'utente quando serve
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Serve la fotocamera per i QR code!", Toast.LENGTH_LONG).show()
        }
    }

    // --- DEFINIZIONE DELLE SCHERMATE (LE ROUTE) ---
    NavHost(navController = navController, startDestination = "home") {

        // A. SCHERMATA HOME
        composable("home") {
            HomeScreen(
                busStopList = busStopList,
                onStopClick = { stop ->
                    // Vai al dettaglio passando il codice
                    navController.navigate("detail/${stop.code}")
                },
                onDeleteClick = { stop ->
                    viewModel.deleteStop(stop)
                },
                onAddClick = {
                    // Prima di andare alla pagina "Aggiungi", chiediamo il permesso fotocamera
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    navController.navigate("add")
                }
            )
        }

        // B. SCHERMATA AGGIUNGI FERMATA
        composable("add") {
            AddStopScreen(
                onSave = { name, code ->
                    viewModel.addStop(name, code)
                    navController.popBackStack() // Torna indietro alla Home
                },
                onCancel = {
                    navController.popBackStack() // Torna indietro senza salvare
                }
            )
        }

        // C. SCHERMATA DETTAGLIO (WEBVIEW)
        composable(
            route = "detail/{stopCode}",
            arguments = listOf(navArgument("stopCode") { type = NavType.StringType })
        ) { backStackEntry ->
            // Recupera il codice passato dalla Home
            val code = backStackEntry.arguments?.getString("stopCode") ?: ""
            DetailScreen(stopCode = code)
        }
    }
}