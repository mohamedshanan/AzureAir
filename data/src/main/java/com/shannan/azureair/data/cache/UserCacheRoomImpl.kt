package com.shannan.azureair.data.cache

import com.shannan.azureair.data.Entity.UserEntity
import com.shannan.azureair.data.roomdb.UserDao
import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.functional.Either
import com.shannan.azureair.domain.interactor.models.User
import com.shannan.azureair.domain.repository.UserCache
import javax.inject.Inject

class UserCacheRoomImpl
@Inject constructor(private val userDao: UserDao) : UserCache {

    override suspend fun getUser(timeInMillis: Long): Either<Failure, User> {
        val userList = userDao.getUsers(timeInMillis)
        return if (userList.isNullOrEmpty()) {
            Either.Left(Failure.ServerError)
        } else {
            Either.Right(userList[0].toDomainUser())
        }
    }

    override suspend fun saveUser(user: User): Either<Failure, User> {
        return when (userDao.insert(UserEntity.empty().fromUser(user))) {
            in Long.MIN_VALUE..0 -> {
                Either.Left(Failure.ServerError)
            }
            else -> Either.Right(user)
        }
    }
}