package kth.findmyflight.service

import kth.findmyflight.model.Flight
import kth.findmyflight.model.FlightData
import okhttp3.ResponseBody
import retrofit2.Response

class FlightRepository {

    suspend fun getDeparturesByAirport(airport: String): Response<FlightData> {
        return try {
            // Make the API call using Retrofit
            val response = RetrofitInstance.api.getDeparturesByAirport("d0b485f593db89b6f5e89eed5486c60c", airport)
            // Return the response from the API
            response
        } catch (e: Exception) {
            // Handle any errors (network issues, etc.)
            Response.error<FlightData>(404, ResponseBody.create(null, "Error fetching data"))
        }
    }

    suspend fun getFlightByFlightNumber(flightNumber: String, flightDate: String): Response<FlightData> {
        return try {
            // Make the API call using Retrofit
            val response = RetrofitInstance.api.getFlightByNr("d0b485f593db89b6f5e89eed5486c60c", flightNumber)
            // Return the response from the API
            response
        } catch (e: Exception) {
            // Handle any errors (network issues, etc.)
            Response.error<FlightData>(404, ResponseBody.create(null, "Error fetching data"))
        }
    }
}
