package com.shannan.azureair.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.shannan.azureair.data.utils.NameListConverter
import com.shannan.azureair.domain.interactor.models.Airport as DomainAirport
import com.shannan.azureair.domain.interactor.models.Coordinate as DomainCoordinate
import com.shannan.azureair.domain.interactor.models.Name as DomainName
import com.shannan.azureair.domain.interactor.models.Names as DomainNames
import com.shannan.azureair.domain.interactor.models.Position as DomainPosition

data class AirportsResponse(
        @SerializedName("AirportResource") val airportResource: AirportResource
) {
    companion object {
        fun empty() = AirportsResponse(AirportResource(Airports(emptyList()), Meta(0)))
    }
}

data class AirportResource(
        @SerializedName("Airports") val airports: Airports,
        @SerializedName("Meta") val meta: Meta
)

data class Airports(
        @SerializedName("Airport") val airport: List<Airport>
)

@Entity
@TypeConverters(NameListConverter::class)
data class Airport(
        @PrimaryKey @SerializedName("AirportCode") val airportCode: String,
        @Embedded @SerializedName("Position") val position: Position,
        @SerializedName("CityCode") val cityCode: String,
        @SerializedName("CountryCode") val countryCode: String,
        @Embedded @SerializedName("Names") val names: Names
) {
    fun toDomainAirport() = DomainAirport(airportCode, position.toDomainPosition(), cityCode, countryCode, names.toDomainNames())
    override fun toString(): String = names.name.value
}

data class Names(
        @Embedded @SerializedName("Name") val name: Name
) {
    fun toDomainNames() = DomainNames(name.toDomainName())
}

data class Name(
        @SerializedName("$") val value: String
) {
    fun toDomainName() = DomainName(value)
}

data class Position(
        @Embedded @SerializedName("Coordinate") val coordinate: Coordinate
) {
    fun toDomainPosition() = DomainPosition(coordinate.toDomainCoordinate())
}

data class Coordinate(
        @SerializedName("Latitude") val latitude: Double?,
        @SerializedName("Longitude") val longitude: Double?
) {
    fun toDomainCoordinate() = DomainCoordinate(latitude, longitude)
}


data class Meta(
        @SerializedName("TotalCount") val totalCount: Int
)