package kth.findmyflight

import AirportScreen
import FlightsVM
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import kth.findmyflight.ui.theme.FindMyFlightTheme

class MainActivity : ComponentActivity() {
    private lateinit var flightVM: FlightsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        flightVM = FlightsVM()
        setContent {
            FindMyFlightTheme {
                AirportScreen(flightVM)
            }
        }
    }
}