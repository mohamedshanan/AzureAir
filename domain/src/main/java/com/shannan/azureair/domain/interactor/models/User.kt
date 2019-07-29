package com.shannan.azureair.domain.interactor.models

import com.shannan.azureair.domain.extension.empty

data class User(val id: Long, val access_token: String, val token_type: String, val expires_in: Long, var expiresAt: Long) {

    companion object {
        fun empty() = User(Long.MAX_VALUE, String.empty(), String.empty(), Long.MAX_VALUE, Long.MAX_VALUE)
    }
}
