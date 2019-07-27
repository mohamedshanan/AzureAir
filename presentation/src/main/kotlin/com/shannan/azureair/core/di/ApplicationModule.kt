
package com.shannan.azureair.core.di

import android.content.Context
import com.shannan.azureair.AndroidApplication
import com.shannan.azureair.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
                // TODO put server url here
                .baseUrl("base url here")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }


    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }
}
