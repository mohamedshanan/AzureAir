package com.shannan.azureair.domain.interactor.models

data class Airport(
        val airportCode: String,
        val position: Position,
        val cityCode: String,
        val countryCode: String,
        val names: Names
)

data class Position(
        val coordinate: Coordinate
)

data class Coordinate(
        val latitude: Double?,
        val longitude: Double?
)

data class Names(
        val name: Name
)

data class Name(
        val value: String
)
