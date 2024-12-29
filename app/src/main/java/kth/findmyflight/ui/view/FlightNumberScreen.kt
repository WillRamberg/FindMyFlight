package kth.findmyflight.ui.view

import FlightsVM
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun FlightNumberScreen(viewModel: FlightsVM) {
    var flightNumber by remember { mutableStateOf("") }
    var flightDate by remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf<String?>(null) }
    val flightData by viewModel.flight // Fetch the FlightData (which contains a single flight)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Flight Number TextField
        TextField(
            value = flightNumber,
            onValueChange = { flightNumber = it },
            label = { Text("Flight Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Flight Date TextField
        TextField(
            value = flightDate,
            onValueChange = { flightDate = it },
            label = { Text("Flight Date (YYYY-MM-DD)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Display input error if any
        inputError?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 8.dp))
        }

        // Submit button
        Button(
            onClick = {
                if (flightNumber.isNotEmpty() && flightDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
                    inputError = null
                    viewModel.fetchFlightByFlightNumber(flightNumber, flightDate)
                } else {
                    inputError = "Please enter a valid flight number and date (YYYY-MM-DD)."
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Submit")
        }

        // Display flight details (if flightData is not null)
        flightData?.let { data ->
            // Assuming flightData contains a list of flights, display the first flight
            val firstFlight = data.data.firstOrNull()
            firstFlight?.let { flight ->
                Text(text = "Flight Status: ${flight.flight_status}")
                Text(text = "Departure: ${flight.departure.airport}")
                Text(text = "Departure Terminal: ${flight.departure.terminal ?: "N/A"}")
                Text(text = "Departure Gate: ${flight.departure.gate ?: "N/A"}")
                Text(text = "Departure Time: ${flight.departure.scheduled}")
                Text(text = "Arrival: ${flight.arrival.airport}")
                Text(text = "Arrival Time: ${flight.arrival.scheduled}")
                Text(text = "Airline: ${flight.airline.name}")
            } ?: run {
                Text(text = "No flight information available.")
            }
        }
    }
}



