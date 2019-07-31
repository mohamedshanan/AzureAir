package com.shannan.azureair.data.remote.auth

import com.nhaarman.mockito_kotlin.given
import com.shannan.azureair.data.UnitTest
import com.shannan.azureair.data.entity.UserEntity
import com.shannan.azureair.data.utils.NetworkHandler
import com.shannan.azureair.domain.exception.AuthenticationFailure
import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.extension.empty
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.models.User
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response


class LufthansaUsersImplTest : UnitTest() {

    private val USER_ID = "Mohamed"
    private val USER_SECRET = "qwerty"
    private val fakeUserEntity = UserEntity(1, "accessToken", "type", 1000, 10000)

    private lateinit var usersRepository: LufthansaUsersImpl

    @Mock
    private lateinit var networkHandler: NetworkHandler
    @Mock
    private lateinit var service: LufthansaAuthService

    @Mock
    private lateinit var authCall: Call<UserEntity>
    @Mock
    private lateinit var authResponse: Response<UserEntity>

    @Before
    fun setUp() {
        usersRepository = LufthansaUsersImpl(networkHandler, service)
    }

    @Test
    fun `should return NetworkConnectionFailure`() {
        given { networkHandler.isConnected }.willReturn(false)
        val response = usersRepository.authenticate(USER_ID, USER_SECRET)
        response shouldEqual Either.Left(Failure.NetworkConnection)
    }

    @Test
    fun `should return AuthenticationFailure`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { authResponse.isSuccessful }.willReturn(false)
        given { authCall.execute() }.willReturn(authResponse)
        given { service.authenticate(USER_ID, USER_SECRET, LufthansaAuthApi.PARAM_GRANT_TYPE) }.willReturn(authCall)

        val response = usersRepository.authenticate(USER_ID, USER_SECRET)
        response shouldEqual Either.Left(AuthenticationFailure)
    }

    @Test(expected = Exception::class)
    fun `should return ServerError`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { usersRepository.authenticate(USER_ID, USER_SECRET) }.willThrow(Exception())

        val response = usersRepository.authenticate(USER_ID, USER_SECRET)

        response shouldEqual Either.Left(Failure.ServerError)
    }

    @Test
    fun `should return default user`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { authResponse.body() }.willReturn(null)
        given { authResponse.isSuccessful }.willReturn(true)
        given { authCall.execute() }.willReturn(authResponse)
        given { service.authenticate(USER_ID, USER_SECRET, LufthansaAuthApi.PARAM_GRANT_TYPE) }.willReturn(authCall)

        val response = usersRepository.authenticate(USER_ID, USER_SECRET)

        response shouldEqual Either.Right(User(Long.MAX_VALUE, String.empty(), String.empty(), Long.MAX_VALUE, Long.MAX_VALUE))
    }

    @Test
    fun `should return user response`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { authResponse.body() }.willReturn(fakeUserEntity)
        given { authResponse.isSuccessful }.willReturn(true)
        given { authCall.execute() }.willReturn(authResponse)
        given { service.authenticate(USER_ID, USER_SECRET, LufthansaAuthApi.PARAM_GRANT_TYPE) }.willReturn(authCall)

        val response = usersRepository.authenticate(USER_ID, USER_SECRET)

        response shouldEqual Either.Right(fakeUserEntity.toDomainUser())
    }

}