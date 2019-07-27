package com.shannan.azureair.domain.repository

import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.models.User

interface UsersRepository {
    fun register(user: User): Either<Failure, User>
    fun login(user: User): Either<Failure, User>
}
