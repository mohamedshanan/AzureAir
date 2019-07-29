package com.shannan.azureair.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shannan.azureair.data.entity.UserEntity
import com.shannan.azureair.domain.interactor.models.Airport

@Database(entities = [UserEntity::class, Airport::class], version = 1, exportSchema = false)
abstract class AzureAirDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun airportDao(): AirportDao
}