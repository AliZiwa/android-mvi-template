package com.template.app.data.repository

import com.template.app.core.network.NetworkResult
import com.template.app.data.local.db.dao.UserDao
import com.template.app.data.mapper.toUser
import com.template.app.domain.model.User
import com.template.app.domain.repository.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : UserRepository {

    override suspend fun getUser(): NetworkResult<User> {
        delay(500)
        val cachedUser = getCachedUser()
        return if (cachedUser != null) {
            NetworkResult.Success(cachedUser)
        } else {
            NetworkResult.Error(message = "User not found")
        }
    }

    override suspend fun getCachedUser(): User? {
        return userDao.getUser()?.toUser()
    }
}
