package com.shannan.azureair.data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shannan.azureair.domain.extension.empty
import com.shannan.azureair.domain.interactor.models.User

@Entity(tableName = "users")
data class UserEntity(@PrimaryKey(autoGenerate = true) val id: Long, val access_token: String, val token_type: String, val expires_in: Long, var expiresAt: Long) {

    companion object {
        fun empty() = UserEntity(Long.MAX_VALUE, String.empty(), String.empty(), Long.MAX_VALUE, Long.MAX_VALUE)
    }

    fun toDomainUser() = User(id, access_token, token_type, expires_in, expiresAt)
    fun fromUser(user: User) = UserEntity(user.id, user.access_token, user.token_type, user.expires_in, user.expiresAt)
}
