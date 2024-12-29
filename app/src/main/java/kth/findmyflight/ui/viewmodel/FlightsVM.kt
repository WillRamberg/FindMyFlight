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
                val flightData = response.body()
                _flights.value = flightData?.data ?: emptyList() // Update the flights state
            } else {
                println("Error: ${response.message()}")
            }
        }
    }
}
