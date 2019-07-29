package com.shannan.azureair.domain.repository

import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.models.User

interface UserCache {
    suspend fun saveUser(user: User): Either<Failure, User>
    suspend fun getUser(timeInMillis: Long): Either<Failure, User>
}
