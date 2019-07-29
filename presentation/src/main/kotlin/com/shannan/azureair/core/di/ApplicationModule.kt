
package com.shannan.azureair.core.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.shannan.azureair.AndroidApplication
import com.shannan.azureair.BuildConfig
import com.shannan.azureair.data.entity.Names
import com.shannan.azureair.data.utils.NamesClassJsonDeserializer
import com.shannan.azureair.data.utils.ScheduleJsonDeserializer
import com.shannan.azureair.data.utils.ScheduleResourceJsonDeserializer
import com.shannan.azureair.data.utils.TerminalClassDeserializer
import com.shannan.azureair.domain.interactor.models.Schedule
import com.shannan.azureair.domain.interactor.models.ScheduleResource
import com.shannan.azureair.domain.interactor.models.Terminal
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val gson = GsonBuilder()
                .registerTypeAdapter(Names::class.java, NamesClassJsonDeserializer())
                .registerTypeAdapter(ScheduleResource::class.java, ScheduleResourceJsonDeserializer())
                .registerTypeAdapter(Schedule::class.java, ScheduleJsonDeserializer())
                .registerTypeAdapter(Terminal::class.java, TerminalClassDeserializer())
                .setLenient()
                .create()

        return Retrofit.Builder()
                .baseUrl("https://api.lufthansa.com/v1/")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }


    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }
}
