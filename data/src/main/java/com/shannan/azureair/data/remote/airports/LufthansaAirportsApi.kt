package com.shannan.azureair.data.remote.airports

import com.shannan.azureair.data.entity.AirportsResponse
import com.shannan.azureair.data.entity.FlightsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

internal interface LufthansaAirportsApi {

    /**
     * Get a list of airports
     */
    @GET(GET_AIRPORTS)
    fun getAirports(@Header(AUTHORIZATION_HEADER) token: String,
                    @Query(PARAM_OFFSET) offset: Int,
                    @Query(PARAM_LIMIT) limit: Int,
                    @Query(PARAM_LANG) lang: String):
            Call<AirportsResponse>

    /**
     * Get a list of flight between two airports in the provided date
     */
    @GET(GET_SCHEDULES)
    fun searchFlights(@Path(PARAM_ORIGIN) origin: String,
                      @Path(PARAM_DESTINATION) destination: String,
                      @Path(PARAM_FROMDATETIME) fromDateTime: String,
                      @Header(AUTHORIZATION_HEADER) token: String,
                      @Query(PARAM_OFFSET) offset: Int,
                      @Query(PARAM_LIMIT) limit: Int):
            Call<FlightsResponse>

    companion object {
        const val BEARER: String = "Bearer "
        const val DEFAULT_LANG: String = "en"

        const val GET_AIRPORTS = "references/airports"
        const val GET_SCHEDULES = "operations/schedules/{origin}/{destination}/{fromDateTime}"
        const val AUTHORIZATION_HEADER: String = "Authorization"
        const val PARAM_OFFSET = "offset"
        const val PARAM_LIMIT = "limit"
        const val PARAM_LANG = "lang"

        const val PARAM_ORIGIN = "origin"
        const val PARAM_DESTINATION = "destination"
        const val PARAM_FROMDATETIME = "fromDateTime"
    }
}
