package kth.findmyflight.service

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
}