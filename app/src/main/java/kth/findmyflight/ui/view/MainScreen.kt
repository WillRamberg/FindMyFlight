package kth.findmyflight.ui.view

import FlightsVM
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: FlightsVM) {
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerContent = { AppDrawer(navController) }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("FindMyFlight") },
                    navigationIcon = {
                        IconButton(onClick = {  }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            content = { padding ->
                NavHost(
                    navController = navController,
                    startDestination = "airportScreen",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("airportScreen") { AirportScreen(viewModel) }
                    composable("flightNumberScreen") { FlightNumberScreen(viewModel) }
                }
            }
        )
    }
}

@Composable
fun AppDrawer(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = { navController.navigate("airportScreen") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            Text("Search by Airport")
        }
        Button(
            onClick = { navController.navigate("flightNumberScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search by Flight Number")
        }
    }
}

