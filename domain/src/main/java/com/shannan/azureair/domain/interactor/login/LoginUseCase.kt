package com.shannan.azureair.domain.interactor.login

import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.UseCase
import com.shannan.azureair.domain.interactor.models.User
import com.shannan.azureair.domain.repository.UsersRepository
import javax.inject.Inject

class LoginUseCase
@Inject constructor(private val usersRepository: UsersRepository) : UseCase<User, LoginUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, User> = usersRepository.login(params.user)

    data class Params(val user: User)
}
