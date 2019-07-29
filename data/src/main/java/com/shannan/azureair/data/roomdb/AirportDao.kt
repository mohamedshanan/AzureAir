package com.shannan.azureair.data.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shannan.azureair.data.Entity.Airport

/**
 * Room data access object for accessing the [Airport] table.
 */
@Dao
interface AirportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Airport>)

    // Do a similar query as the search API:
    // Look for airports that contain the query string in the name
    @Query("SELECT * FROM Airport WHERE (cityCode LIKE :queryString) OR (airportCode LIKE " +
            ":queryString) OR (value LIKE :queryString) LIMIT 4")
    fun searchAirports(queryString: String): List<Airport>

    // Do a similar query as the search API:
    // Look for airports that contain the query string in the name
    @Query("SELECT * FROM Airport")
    fun getAirports(): List<Airport>

    @Query("SELECT * FROM Airport WHERE airportCode IN (:codes)")
    fun getAirportsListByCodes(codes: List<String>): List<Airport>

}