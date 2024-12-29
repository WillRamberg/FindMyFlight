package kth.findmyflight.service

import kth.findmyflight.model.Flight
import kth.findmyflight.model.FlightData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AviationStackService {
    @GET("flights")
    suspend fun getDeparturesByAirport(
        @Query("access_key") apiKey: String,
        @Query("dep_iata") airportCode: String
    ): Response<FlightData>

    @GET("flights")
    suspend fun getFlightByNr(
        @Query("access_key") apiKey: String,
        @Query("flight_iata") flightNr: String,
        //@Query("flight_date") airline: String
    ): Response<FlightData>
}