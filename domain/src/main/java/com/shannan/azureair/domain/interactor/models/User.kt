package com.shannan.azureair.domain.interactor.models

import com.shannan.azureair.domain.extension.empty

data class User(var email: String, var name: String, var phone: String?, var token: String?) {

    companion object {
        fun empty() = User(String.empty(), String.empty(), null, null)
    }
}
