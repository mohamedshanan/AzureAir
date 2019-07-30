package com.shannan.azureair.domain.repository

import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.models.User

interface UsersRepository {
    fun authenticate(clientId: String, clientSecret: String): Either<Failure, User>
}
