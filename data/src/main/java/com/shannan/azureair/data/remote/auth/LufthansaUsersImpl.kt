package com.shannan.azureair.data.remote.auth

import com.shannan.azureair.data.entity.UserEntity
import com.shannan.azureair.data.remote.auth.LufthansaAuthApi.Companion.PARAM_GRANT_TYPE
import com.shannan.azureair.data.utils.NetworkHandler
import com.shannan.azureair.domain.exception.AuthenticationFailure
import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.models.User
import com.shannan.azureair.domain.repository.UsersRepository
import retrofit2.Call
import javax.inject.Inject

class LufthansaUsersImpl
@Inject constructor(private val networkHandler: NetworkHandler,
                    private val service: LufthansaAuthService) : UsersRepository {

    override fun authenticate(clientId: String, clientSecret: String): Either<Failure, User> {
        return when (networkHandler.isConnected) {
            true -> request(service.authenticate(clientId, clientSecret, PARAM_GRANT_TYPE), { it.toDomainUser() }, UserEntity.empty())
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(AuthenticationFailure)
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }

}