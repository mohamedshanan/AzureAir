package com.shannan.azureair.data.remote

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LufthansaService
@Inject constructor(retrofit: Retrofit) : LufthansaApi {

    private val lufthansaApi by lazy { retrofit.create(LufthansaApi::class.java) }

    override fun authenticate(clientId: String, clientSecret: String, grantType: String) =
            lufthansaApi.authenticate(clientId, clientSecret, grantType)

    override fun getAirports(token: String, offset: Int, limit: Int, lang: String) =
            lufthansaApi.getAirports(token, offset, limit, lang)

    override fun searchFlights(origin: String, destination: String, fromDateTime: String, token: String, offset: Int, limit: Int) =
            lufthansaApi.searchFlights(origin, destination, fromDateTime, token, offset, limit)

}
