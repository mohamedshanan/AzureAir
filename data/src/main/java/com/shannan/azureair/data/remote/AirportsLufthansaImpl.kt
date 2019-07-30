package com.shannan.azureair.data.remote

import com.shannan.azureair.data.entity.AirportsResponse
import com.shannan.azureair.data.remote.LufthansaApi.Companion.BEARER
import com.shannan.azureair.data.remote.LufthansaApi.Companion.DEFAULT_LANG
import com.shannan.azureair.data.utils.NetworkHandler
import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.exception.GetAirportsFailure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.models.Airport
import com.shannan.azureair.domain.repository.AirportsRepository
import retrofit2.Call
import javax.inject.Inject

class AirportsLufthansaImpl
@Inject constructor(private val networkHandler: NetworkHandler,
                    private val service: LufthansaService) : AirportsRepository {

    companion object {
        private const val LIMIT = 100
    }

    private var offset = 0

    override fun getAirports(token: String): Either<Failure, List<Airport>> {

        return when (networkHandler.isConnected) {
            true -> requestAirports(token, service.getAirports(BEARER.plus(token), offset, LIMIT, DEFAULT_LANG))
            false, null -> Either.Left(Failure.NetworkConnection)
        }

    }

    override fun searchAirports(token: String, query: String): Either<Failure, List<Airport>> {
        return when (networkHandler.isConnected) {
            true -> requestAirports(token, service.getAirports(BEARER.plus(token), offset, LIMIT, DEFAULT_LANG))
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }

    private fun requestAirports(token: String, call: Call<AirportsResponse>): Either<Failure, List<Airport>> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                false -> Either.Left(Failure.ServerError)
                true -> {
                    offset += LIMIT

                    // Request next batch of airports
                    if (offset < response.body()?.airportResource?.meta?.totalCount ?: offset + LIMIT) {
                        getAirports(token)
                    }

                    response?.body().let {
                        Either.Right(it!!.airportResource.airports.airport.map { it.toDomainAirport() })
                    }
                }
            }
        } catch (exception: Throwable) {
            Either.Left(GetAirportsFailure())
        }
    }

}