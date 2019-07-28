package com.shannan.azureair.domain.interactor.searchschedules

import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.UseCase
import com.shannan.azureair.domain.interactor.models.ScheduleRequest
import com.shannan.azureair.domain.interactor.models.SchedulesResponse
import com.shannan.azureair.domain.repository.SchedulesRepository
import javax.inject.Inject

class SearchSchedulesUseCase
@Inject constructor(private val schedulesRepository: SchedulesRepository) : UseCase<List<SchedulesResponse>, SearchSchedulesUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<SchedulesResponse>> = schedulesRepository.search(params.request)

    data class Params(val request: ScheduleRequest)
}
