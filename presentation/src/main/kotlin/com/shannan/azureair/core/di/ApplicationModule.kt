
package com.shannan.azureair.core.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.shannan.azureair.AndroidApplication
import com.shannan.azureair.BuildConfig
import com.shannan.azureair.data.cache.AirportsCacheRoomImpl
import com.shannan.azureair.data.cache.UserCacheRoomImpl
import com.shannan.azureair.data.entity.Airport
import com.shannan.azureair.data.entity.Names
import com.shannan.azureair.data.remote.airports.LufthansaAirportsImpl
import com.shannan.azureair.data.remote.auth.LufthansaUsersImpl
import com.shannan.azureair.data.roomdb.AirportDao
import com.shannan.azureair.data.roomdb.AzureAirDatabase
import com.shannan.azureair.data.roomdb.UserDao
import com.shannan.azureair.data.utils.*
import com.shannan.azureair.domain.interactor.models.Schedule
import com.shannan.azureair.domain.interactor.models.ScheduleResource
import com.shannan.azureair.domain.interactor.models.Terminal
import com.shannan.azureair.domain.repository.AirportCache
import com.shannan.azureair.domain.repository.AirportsRepository
import com.shannan.azureair.domain.repository.UserCache
import com.shannan.azureair.domain.repository.UsersRepository
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

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val gson = GsonBuilder()
                .registerTypeAdapter(Airport::class.java, AirportJsonDeserializer())
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
            val loggingInterceptor =
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    private val keyRoomDatabase: AzureAirDatabase = Room.databaseBuilder(
            application.applicationContext,
            AzureAirDatabase::class.java,
            "AzureAirDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesRoomDatabase(): AzureAirDatabase {
        return keyRoomDatabase
    }

    @Provides
    @Singleton
    fun providesUserDao(): UserDao {
        return keyRoomDatabase.userDao()
    }

    @Provides
    @Singleton
    fun providesAirportDao(): AirportDao {
        return keyRoomDatabase.airportDao()
    }

    @Provides
    @Singleton
    fun providesUserRepository(usersRepository: LufthansaUsersImpl): UsersRepository = usersRepository

    @Provides
    @Singleton
    fun providesUserCache(userCache: UserCacheRoomImpl): UserCache = userCache

    @Provides
    @Singleton
    fun providesAirportsRepository(repositoryAirports: LufthansaAirportsImpl): AirportsRepository = repositoryAirports

    @Provides
    @Singleton
    fun providesAirportsCache(airportCache: AirportsCacheRoomImpl): AirportCache = airportCache
}
