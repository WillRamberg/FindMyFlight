import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kth.findmyflight.model.Flight
import kth.findmyflight.model.FlightData
import kth.findmyflight.service.FlightRepository
import retrofit2.Response

class FlightsVM : ViewModel() {
    private val flightRepository = FlightRepository()

    private val _flights = mutableStateOf<List<Flight>>(emptyList())
    val flights: State<List<Flight>> = _flights

    fun fetchDeparturesByAirport(airportCode: String) {
        viewModelScope.launch {
            val response: Response<FlightData> = flightRepository.getDeparturesByAirport(airportCode)
            if (response.isSuccessful) {
                response.body()?.let { flightData ->
                    // Sort flights by scheduled departure time
                    _flights.value = flightData.data.sortedBy { it.departure.scheduled }
                } ?: run {
                    _flights.value = emptyList() // In case the data is null
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: response.message()
                println("Error: ${response.code()} - $errorMessage")
            }
        }
    }

    private val _flight = mutableStateOf<FlightData?>(null)
    val flight: State<FlightData?> = _flight

    fun fetchFlightByFlightNumber(flightNumber: String, flightDate: String) {
        viewModelScope.launch {
            val response: Response<FlightData> = flightRepository.getFlightByFlightNumber(flightNumber, flightDate)
            if (response.isSuccessful) {
                _flight.value = response.body() // Update the single flight state
                System.out.println(response)
            } else {
                // Log more detailed error information
                val errorMessage = response.errorBody()?.string() ?: response.message()
                println("Error: ${response.code()} - $errorMessage")
            }
        }
    }
}
