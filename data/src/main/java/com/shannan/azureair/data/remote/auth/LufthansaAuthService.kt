package com.shannan.azureair.data.remote.auth

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LufthansaAuthService
@Inject constructor(retrofit: Retrofit) : LufthansaAuthApi {

    private val lufthansaApi by lazy { retrofit.create(LufthansaAuthApi::class.java) }

    override fun authenticate(clientId: String, clientSecret: String, grantType: String) =
            lufthansaApi.authenticate(clientId, clientSecret, grantType)
}
