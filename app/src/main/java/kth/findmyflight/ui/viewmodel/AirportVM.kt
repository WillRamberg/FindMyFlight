package kth.findmyflight.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kth.findmyflight.model.FlightData
import kth.findmyflight.service.FlightRepository
import retrofit2.Response

class FlightsVM : ViewModel() {
    private val flightRepository = FlightRepository()

    fun fetchDeparturesByAirport(airportCode: String) {
        viewModelScope.launch {
            val response: Response<FlightData> = flightRepository.getDeparturesByAirport(airportCode)
            if (response.isSuccessful) {
                val flightData = response.body()
                // Do something with the flight data (e.g., update UI)
                flightData?.data?.forEach {
                    println("Flight: ${it.arrival.airport}, Departure: ${it.departure.airport}")
                }
            } else {
                // Handle error (response is not successful)
                println("Error: ${response.message()}")
            }
        }
    }
}