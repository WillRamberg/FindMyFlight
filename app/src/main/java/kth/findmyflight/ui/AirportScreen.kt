import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kth.findmyflight.model.Flight

@Composable
fun AirportScreen(viewModel: FlightsVM) {
    var airport by remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf<String?>(null) }
    val flights by viewModel.flights

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Airport Name/Code TextField
        TextField(
            value = airport,
            onValueChange = { airport = it },
            label = { Text("Airport") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Error message if any
        inputError?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 8.dp))
        }

        // Submit Button
        Button(
            onClick = {
                if (airport.isNotEmpty()) {
                    inputError = null
                    viewModel.fetchDeparturesByAirport(airport)
                } else {
                    inputError = "Please enter a valid airport."
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Submit")
        }

        // Flight List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(flights) { flight ->
                FlightItem(flight)
            }
        }
    }
}

@Composable
fun FlightItem(flight: Flight) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight()
    ) {
        Text(text = "Flight Status: ${flight.flight_status}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Departure: ${flight.departure.airport}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Arrival: ${flight.arrival.airport}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Airline: ${flight.airline.name}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Departure Time: ${flight.departure.scheduled}", style = MaterialTheme.typography.bodySmall)
    }
}
