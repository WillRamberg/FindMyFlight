package kth.findmyflight

import FlightsVM
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kth.findmyflight.ui.view.MainScreen
import kth.findmyflight.ui.theme.FindMyFlightTheme

class MainActivity : ComponentActivity() {
    private lateinit var flightVM: FlightsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flightVM = FlightsVM() // Initialize the ViewModel

        setContent {
            FindMyFlightTheme {
                // Use MainScreen to include the navigation and drawer menu
                MainScreen(viewModel = flightVM)
            }
        }
    }
}
