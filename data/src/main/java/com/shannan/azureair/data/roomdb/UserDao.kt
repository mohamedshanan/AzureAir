package com.shannan.azureair.data.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shannan.azureair.data.Entity.UserEntity

@Dao
interface UserDao {
    // get saved token if it is not expired
    @Query("SELECT * FROM users WHERE expiresAt > :currentTime LIMIT 1")
    fun getUsers(currentTime: Long): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity): Long
}