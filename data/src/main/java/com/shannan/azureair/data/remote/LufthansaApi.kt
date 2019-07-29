package com.shannan.azureair.data.remote

import com.shannan.azureair.data.entity.AirportsResponse
import com.shannan.azureair.data.entity.FlightsResponse
import com.shannan.azureair.data.entity.UserEntity
import retrofit2.Call
import retrofit2.http.*

internal interface LufthansaApi {

    companion object {
        const val AIRPORT_DB_NAME: String = "Airports.db"
        const val LH_GRANT_TYPE: String = "client_credentials"
        const val AUTHORIZATION_HEADER: String = "Authorization"
        const val BEARER: String = "Bearer "
        const val DEFAULT_LANG: String = "en"

        const val GET_ACCESS_TOKEN: String = "oauth/token"
        const val GET_AIRPORTS: String = "references/airports"
        const val GET_SCHEDULES: String = "operations/schedules/{origin}/{destination}/{fromDateTime}"
    }

    /**
     * Get access token
     */
    @FormUrlEncoded
    @POST(GET_ACCESS_TOKEN)
    fun authenticate(@Field("client_id") clientId: String,
                     @Field("client_secret") clientSecret: String,
                     @Field("grant_type") grantType: String):
            Call<UserEntity>

    /**
     * Get a list of airports
     */
    @GET(GET_AIRPORTS)
    fun getAirports(@Header(AUTHORIZATION_HEADER) token: String,
                    @Query("offset") offset: Int,
                    @Query("limit") limit: Int,
                    @Query("lang") lang: String):
            Call<AirportsResponse>

    /**
     * Get a list of flight between two airports in the provided date
     */
    @GET(GET_SCHEDULES)
    fun searchFlights(@Path("origin") origin: String,
                      @Path("destination") destination: String,
                      @Path("fromDateTime") fromDateTime: String,
                      @Header(AUTHORIZATION_HEADER) token: String,
                      @Query("offset") offset: Int,
                      @Query("limit") limit: Int):
            Call<FlightsResponse>
}
