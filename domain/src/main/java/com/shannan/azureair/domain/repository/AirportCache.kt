package com.shannan.azureair.domain.repository

import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.models.Airport

interface AirportCache {
    fun getAirports(token: String): Either<Failure, List<Airport>>
    fun searchAirports(token: String, query: String): Either<Failure, List<Airport>>
    fun getAirportsListByCodes(query: List<String>): Either<Failure, List<Airport>>
}
