package com.shannan.azureair.domain.repository

import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.UseCase
import com.shannan.azureair.domain.interactor.models.User

interface UserCache {
    suspend fun saveUser(user: User): Either<Failure, User>
    suspend fun deleteKey(): Either<Failure, UseCase.None>
    suspend fun getUser(): Either<Failure, User>
}
