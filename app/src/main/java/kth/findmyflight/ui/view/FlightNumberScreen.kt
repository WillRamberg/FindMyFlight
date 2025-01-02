package kth.findmyflight.ui.view

import FlightsVM
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        // Input Section
        Text(
            text = "Find Flight Details",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

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
                .padding(bottom = 16.dp)
        )

        // Display input error if any
        inputError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Submit Button
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

        Spacer(modifier = Modifier.height(24.dp))

        // Display Flight Details
        flightData?.let { data ->
            val firstFlight = data.data.firstOrNull()
            firstFlight?.let { flight ->
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item { SectionTitle("Flight Information") }
                    item { DetailRow("Flight Status", flight.flight_status) }
                    item { DetailRow("Airline", flight.airline.name) }
                    item { DetailRow("Flight Number", flight.flight.number) }
                    item { DetailRow("Flight Iata", flight.flight.iata) }

                    item { Spacer(modifier = Modifier.height(16.dp)) }

                    item { SectionTitle("Departure Details") }
                    item { DetailRow("Airport", flight.departure.airport) }
                    item { DetailRow("Terminal", flight.departure.terminal ?: "N/A") }
                    item { DetailRow("Gate", flight.departure.gate ?: "N/A") }
                    item { DetailRow("Scheduled", flight.departure.scheduled.take(4) + " " + flight.departure.scheduled.substring(11, 16)) }
                    item { DetailRow("Estimated", flight.departure.estimated.take(4) + " " + flight.departure.estimated.substring(11, 16)) }
                    item { DetailRow("Actual", flight.departure.actual.take(4) + " " + flight.departure.actual.substring(11, 16)) }


                    item { Spacer(modifier = Modifier.height(16.dp)) }

                    item { SectionTitle("Arrival Details") }
                    item { DetailRow("Airport", flight.arrival.airport) }
                    item { DetailRow("Terminal", flight.arrival.terminal ?: "N/A") }
                    item { DetailRow("Gate", flight.arrival.gate ?: "N/A") }
                    item { DetailRow("Scheduled", flight.arrival.scheduled.take(4) + " " + flight.arrival.scheduled.substring(11, 16)) }
                    item { DetailRow("Estimated", flight.arrival.estimated.take(4) + " " + flight.arrival.estimated.substring(11, 16)) }
                    item { DetailRow("Actual", flight.arrival.actual.take(4) + " " + flight.arrival.actual.substring(11, 16)) }



                    item { Spacer(modifier = Modifier.height(16.dp)) }

                    item { SectionTitle("Aircraft Details") }
                    item { DetailRow("Aircraft Iata", flight.aircraft.iata) }
                    item { DetailRow("Registration", flight.aircraft.registration) }

                    item { Spacer(modifier = Modifier.height(16.dp)) }

                    flight.flight.codeshared?.let { codeshared ->
                        item { SectionTitle("Codeshared Information") }
                        item { DetailRow("Airline", codeshared.airline_name) }
                        item { DetailRow("Flight Number", codeshared.flight_number) }
                    }
                }
            } ?: run {
                Text(
                    text = "No flight information available.",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
    }
}




