package kth.findmyflight.ui.view

import FlightsVM
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kth.findmyflight.model.Flight
import kth.findmyflight.ui.theme.FindMyFlightTheme // Import your theme package

@Composable
fun AirportScreen(viewModel: FlightsVM) {
    var airport by remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf<String?>(null) }
    val flights by viewModel.flights

    // Wrap everything in the custom theme
    FindMyFlightTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Input for airport
            TextField(
                value = airport,
                onValueChange = { airport = it },
                label = { Text("Airport Code (IATA)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            // Error message
            inputError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 8.dp))
            }

            // Submit button
            Button(
                onClick = {
                    if (airport.isNotEmpty()) {
                        inputError = null
                        viewModel.fetchDeparturesByAirport(airport)
                    } else {
                        inputError = "Please enter a valid airport code."
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Search Flights")
            }

            // Display flight items
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(flights) { flight ->
                    FlightItemCard(flight)
                }
            }
        }
    }
}

@Composable
fun FlightItemCard(flight: Flight) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        // Row for departure and arrival times
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Departure column
            Column(
                modifier = Modifier.weight(1f), // This will allow the departure column to take available space
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = flight.departure.scheduled?.substring(11, 16) ?: "N/A", // Extracting time (HH:mm)
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = flight.departure.airport ?: "Unknown Airport", // Fallback for null
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = Int.MAX_VALUE,  // Allow multiple lines
                    overflow = TextOverflow.Visible  // Avoid truncating
                )
                Text(
                    text = flight.departure.iata ?: "Unknown IATA",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Direct flight icon centered
            Box(
                modifier = Modifier
                    .size(48.dp)  // Icon size control
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Flight,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Arrival column - Ensuring it stays aligned to the far-right side
            Column(
                modifier = Modifier.weight(1f), // Allow this column to take the remaining space, pushing it to the right
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = flight.arrival.scheduled?.substring(11, 16) ?: "N/A", // Extracting time (HH:mm)
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.End)
                )
                Text(
                    text = flight.arrival.airport ?: "Unknown Airport", // Fallback for null
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.End),  // Ensure it's right-aligned
                    maxLines = Int.MAX_VALUE,  // Allow multiple lines
                    overflow = TextOverflow.Visible  // Avoid truncating
                )
                Text(
                    text = flight.arrival.iata ?: "Unknown IATA",
                    modifier = Modifier.align(Alignment.End),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Row for airline and flight number
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = flight.airline.name ?: "Unknown Airline", // Fallback for null
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = flight.flight.iata ?: "Unknown Flight Number", // Fallback for null
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}







