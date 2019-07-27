package com.shannan.azureair.domain.interactor.getcacheduser

import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.UseCase
import com.shannan.azureair.domain.interactor.models.User
import com.shannan.azureair.domain.repository.UserCache
import javax.inject.Inject

class GetCachedUserUseCase
@Inject constructor(private val cache: UserCache) : UseCase<User, GetCachedUserUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, User> = cache.getUser()

    data class Params(val none: None)
}
