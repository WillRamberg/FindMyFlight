package kth.findmyflight.model

data class FlightData(
    val data: List<Flight>
)

data class Flight(
    val flight_date: String,
    val flight_status: String,
    val departure: Departure,
    val arrival: Arrival,
    val airline: Airline,
    val flight: FlightDetails,
    val aircraft: Aircraft
)

data class Departure(
    val airport: String,
    val timezone: String,
    val iata: String,
    val icao: String,
    val terminal: String?,
    val gate: String?,
    val delay: Int?,
    val scheduled: String,
    val estimated: String,
    val actual: String
)

data class Arrival(
    val airport: String,
    val timezone: String,
    val iata: String,
    val icao: String,
    val terminal: String?,
    val gate: String?,
    val baggage: String?,
    val delay: Int?,
    val scheduled: String,
    val estimated: String,
    val actual: String
)

data class Airline(
    val name: String,
    val iata: String,
    val icao: String
)

data class FlightDetails(
    val number: String,
    val iata: String,
    val icao: String,
    val registration: String,
    val codeshared: Codeshared?
)

data class Aircraft(
    val iata: String,
    val icao: String,
    val registration: String
)

data class Codeshared(
    val airline_name: String,
    val airline_iata: String,
    val airline_icao: String,
    val flight_number: String,
    val flight_iata: String,
    val flight_icao: String
)

