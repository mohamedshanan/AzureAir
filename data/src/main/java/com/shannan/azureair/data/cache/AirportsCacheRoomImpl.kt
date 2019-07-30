package com.shannan.azureair.data.cache

import com.shannan.azureair.data.roomdb.AirportDao
import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.repository.AirportCache
import javax.inject.Inject
import com.shannan.azureair.data.entity.Airport as AirportEntity
import com.shannan.azureair.domain.interactor.models.Airport as DomainAirport

class AirportsCacheRoomImpl
@Inject constructor(private val dao: AirportDao) : AirportCache {

    override fun getAirports(token: String): Either<Failure, List<DomainAirport>> {

        val airportsList: List<AirportEntity> = dao.getAirports()

        return if (airportsList.isNullOrEmpty()) {
            Either.Left(Failure.ServerError)
        } else {
            Either.Right(airportsList.map { it.toDomainAirport() })
        }
    }

    override fun searchAirports(token: String, query: String): Either<Failure, List<DomainAirport>> {

        val airportsList: List<AirportEntity> = dao.searchAirports(query)

        return if (airportsList.isNullOrEmpty()) {
            Either.Left(Failure.ServerError)
        } else {
            Either.Right(airportsList.map { it.toDomainAirport() })
        }
    }

    override fun getAirportsListByCodes(query: List<String>): Either<Failure, List<DomainAirport>> {
        val airportsList = dao.getAirportsListByCodes(query)
        return if (airportsList.isNullOrEmpty()) {
            Either.Left(Failure.ServerError)
        } else {
            Either.Right(airportsList.map { it.toDomainAirport() })
        }
    }
}