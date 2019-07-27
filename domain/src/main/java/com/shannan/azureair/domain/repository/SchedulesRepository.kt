package com.shannan.azureair.domain.repository

import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.models.ScheduleRequest
import com.shannan.azureair.domain.interactor.models.SchedulesResponse

interface SchedulesRepository {
    fun search(request: ScheduleRequest): Either<Failure, List<SchedulesResponse>>
}
