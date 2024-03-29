package com.shannan.azureair.core

object Constants {
    object Prefs {
        const val SHARED_PREFERENCES_SETTINGS: String = "shared_preferences_settings"
        const val IS_AIRPORTS_CACHED: String = "is_airports_cached"
    }

    object IntentPassing {
        const val FLIGHT_PARAMETERS: String = "flight_parameters"
        const val AIRPORTS_CODES: String = "airports_codes"
    }

    const val BASE_URL: String = "https://api.lufthansa.com/v1/"
    const val AIRPORT_DB_NAME: String = "Airports.db"
    const val LH_GRANT_TYPE: String = "client_credentials"
    const val AUTHORIZATION_HEADER: String = "Authorization"
    const val BEARER: String = "Bearer "
    const val DEFAULT_LANG: String = "en"
}