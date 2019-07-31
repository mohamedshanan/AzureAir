package com.shannan.azureair.data.remote.airports

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LufthansaAirportsService
@Inject constructor(retrofit: Retrofit) : LufthansaAirportsApi {

    private val lufthansaApi by lazy { retrofit.create(LufthansaAirportsApi::class.java) }

    override fun getAirports(token: String, offset: Int, limit: Int, lang: String) =
            lufthansaApi.getAirports(token, offset, limit, lang)

    override fun searchFlights(origin: String, destination: String, fromDateTime: String, token: String, offset: Int, limit: Int) =
            lufthansaApi.searchFlights(origin, destination, fromDateTime, token, offset, limit)

}
