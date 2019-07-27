package com.shannan.azureair.domain.interactor.models

data class SchedulesResponse(
        val ScheduleResource: ScheduleResource
)

data class ScheduleResource(
        val schedule: List<Schedule>
)

data class Schedule(
        val totalJourney: TotalJourney?,
        val flight: List<Flight>
)

data class Flight(
        val departure: Departure,
        val arrival: Arrival,
        val marketingCarrier: MarketingCarrier,
        val operatingCarrier: OperatingCarrier,
        val equipment: Equipment,
        val details: Details
)

data class OperatingCarrier(
        val airlineID: String
)

data class Arrival(
        val airportCode: String,
        val scheduledTimeLocal: ScheduledTimeLocal,
        val terminal: Terminal
)

data class ScheduledTimeLocal(
        val dateTime: String
)

data class Terminal(
        val name: String
)

data class Equipment(
        val aircraftCode: String
)

data class MarketingCarrier(
        val airlineID: String,
        val flightNumber: Int
)

data class Departure(
        val airportCode: String,
        val scheduledTimeLocal: ScheduledTimeLocal,
        val terminal: Terminal
)

data class Details(
        val stops: Stops,
        val daysOfOperation: Int,
        val datePeriod: DatePeriod
)

data class DatePeriod(
        val effective: String,
        val expiration: String
)

data class Stops(
        val stopQuantity: Int
)

data class TotalJourney(
        val duration: String
)