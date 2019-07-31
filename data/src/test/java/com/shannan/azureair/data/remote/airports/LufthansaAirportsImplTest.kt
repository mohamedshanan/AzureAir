package com.shannan.azureair.data.remote.airports

import com.nhaarman.mockito_kotlin.given
import com.shannan.azureair.data.UnitTest
import com.shannan.azureair.data.entity.*
import com.shannan.azureair.data.utils.NetworkHandler
import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.exception.GetAirportsFailure
import com.shannan.azureair.domain.functional.Either
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class LufthansaAirportsImplTest : UnitTest() {

    private val token = "token"
    private val caiCoordinate = Coordinate(30.1127, 31.3999)
    private val caiPosition = Position(caiCoordinate)
    private val name = Name("Cairo")
    private val names = Names(name)
    private val fakeAirport = Airport("CAI", caiPosition, "CAI", "EG", names)
    private val airportsList = mutableListOf(fakeAirport)
    private val airports = Airports(airportsList)
    private val meta = Meta(5)
    private val airportsResource = AirportResource(airports, meta)
    private val fakeResponse = AirportsResponse(airportsResource)

    private lateinit var airportsRepository: LufthansaAirportsImpl

    @Mock
    private lateinit var networkHandler: NetworkHandler
    @Mock
    private lateinit var service: LufthansaAirportsService

    @Mock
    private lateinit var airportsCall: Call<AirportsResponse>
    @Mock
    private lateinit var airportsResponse: Response<AirportsResponse>

    @Before
    fun setUp() {
        airportsRepository = LufthansaAirportsImpl(networkHandler, service)
    }

    @Test
    fun `should return NetworkConnectionFailure`() {
        given { networkHandler.isConnected }.willReturn(false)
        val response = airportsRepository.getAirports(token)
        response shouldEqual Either.Left(Failure.NetworkConnection)
    }

    @Test(expected = Exception::class)
    fun `should return ServerError`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { airportsRepository.getAirports(token) }.willThrow(Exception())

        val response = airportsRepository.getAirports(token)

        response shouldEqual Either.Left(GetAirportsFailure)
    }

    @Test
    fun `should return airport response`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { airportsResponse.isSuccessful }.willReturn(false)
        given { airportsCall.execute() }.willReturn(airportsResponse)
        given { service.getAirports(token, 0, 10, "EN") }.willReturn(airportsCall)

        val response = airportsRepository.getAirports(token)

        response shouldEqual Either.Right(fakeAirport.toDomainAirport())
    }


    @Test
    fun `should return GetAirportsFailure`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { airportsResponse.isSuccessful }.willReturn(false)
        given { airportsCall.execute() }.willReturn(airportsResponse)
        given { service.getAirports(token, 0, 10, "EN") }.willReturn(airportsCall)

        val response = airportsRepository.getAirports(token)
        response shouldEqual Either.Left(GetAirportsFailure)
    }

}