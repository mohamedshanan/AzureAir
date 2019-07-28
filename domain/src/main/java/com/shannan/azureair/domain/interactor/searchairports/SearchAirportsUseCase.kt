package com.shannan.azureair.domain.interactor.searchairports

import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.UseCase
import com.shannan.azureair.domain.interactor.models.Airport
import com.shannan.azureair.domain.repository.AirportsRepository
import javax.inject.Inject

class SearchAirportsUseCase
@Inject constructor(private val airportsRepository: AirportsRepository) : UseCase<List<Airport>, SearchAirportsUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Airport>> = airportsRepository.searchAirports(params.token, params.query)

    data class Params(val token: String, val query: String?)
}
