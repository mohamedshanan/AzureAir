package com.shannan.azureair.domain.interactor.models

import java.io.Serializable

data class ScheduleRequest(
        var origin: String,
        var destination: String,
        var fromDateTime: String
) : Serializable