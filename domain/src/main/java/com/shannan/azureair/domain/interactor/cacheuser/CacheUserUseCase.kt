package com.shannan.azureair.domain.interactor.cacheuser

import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.UseCase
import com.shannan.azureair.domain.interactor.models.User
import com.shannan.azureair.domain.repository.UserCache
import javax.inject.Inject

class CacheUserUseCase
@Inject constructor(private val userCache: UserCache) : UseCase<User, CacheUserUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, User> = userCache.saveUser(params.user)

    data class Params(val user: User)
}
