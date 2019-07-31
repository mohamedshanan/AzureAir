package com.shannan.azureair.data.remote.auth

import com.shannan.azureair.data.entity.UserEntity
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface LufthansaAuthApi {

    /**
     * Get access token
     */
    @FormUrlEncoded
    @POST(GET_ACCESS_TOKEN)
    fun authenticate(@Field(PARAM_CLIENT_ID) clientId: String,
                     @Field(PARAM_CLIENT_SECRET) clientSecret: String,
                     @Field(PARAM_GRANT_TYPE) grantType: String):
            Call<UserEntity>


    companion object {

        const val GET_ACCESS_TOKEN = "oauth/token"
        const val PARAM_CLIENT_ID = "client_id"
        const val PARAM_CLIENT_SECRET = "client_secret"
        const val PARAM_GRANT_TYPE = "grant_type"
    }
}
