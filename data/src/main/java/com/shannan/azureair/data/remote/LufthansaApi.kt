package com.shannan.azureair.data.remote

import com.shannan.azureair.data.entity.AirportsResponse
import com.shannan.azureair.data.entity.FlightsResponse
import com.shannan.azureair.data.entity.UserEntity
import retrofit2.Call
import retrofit2.http.*

internal interface LufthansaApi {

    /**
     * Get access token
     */
    @FormUrlEncoded
    @POST(GET_ACCESS_TOKEN)
    fun authenticate(@Field(PARAM_CLIENT_ID) clientId: String,
                     @Field(PARAM_CLIENT_SECRET) clientSecret: String,
                     @Field(PARAM_GRANT_TYPE) grantType: String):
            Call<UserEntity>

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
        const val LH_GRANT_TYPE: String = "client_credentials"
        const val BEARER: String = "Bearer "
        const val DEFAULT_LANG: String = "en"

        const val GET_ACCESS_TOKEN = "oauth/token"
        const val GET_AIRPORTS = "references/airports"
        const val GET_SCHEDULES = "operations/schedules/{origin}/{destination}/{fromDateTime}"
        const val AUTHORIZATION_HEADER: String = "Authorization"
        const val PARAM_CLIENT_ID = "client_id"
        const val PARAM_CLIENT_SECRET = "client_secret"
        const val PARAM_GRANT_TYPE = "grant_type"
        const val PARAM_OFFSET = "offset"
        const val PARAM_LIMIT = "limit"
        const val PARAM_LANG = "lang"

        const val PARAM_ORIGIN = "origin"
        const val PARAM_DESTINATION = "destination"
        const val PARAM_FROMDATETIME = "fromDateTime"
    }
}
