package com.template.app.data.repository

import com.template.app.core.network.NetworkResult
import com.template.app.data.local.datastore.SessionDataStore
import com.template.app.data.local.db.dao.UserDao
import com.template.app.data.mapper.toEntity
import com.template.app.domain.model.User
import com.template.app.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sessionDataStore: SessionDataStore,
    private val userDao: UserDao,
) : AuthRepository {

    override suspend fun login(email: String, password: String): NetworkResult<User> {
        // Simulate network delay
        delay(1000)

        // Fake validation
        if (email == "error@test.com") {
            return NetworkResult.Error(code = 401, message = "Invalid credentials")
        }

        val user = User(
            id = "user-1",
            email = email,
            displayName = email.substringBefore("@"),
            avatarUrl = null,
        )

        sessionDataStore.saveSession(
            token = "fake-token-${System.currentTimeMillis()}",
            userId = user.id,
            email = user.email,
            displayName = user.displayName,
        )
        userDao.insertUser(user.toEntity())

        return NetworkResult.Success(user)
    }

    override suspend fun register(
        email: String,
        password: String,
        displayName: String,
    ): NetworkResult<User> {
        delay(1000)

        val user = User(
            id = "user-${System.currentTimeMillis()}",
            email = email,
            displayName = displayName,
            avatarUrl = null,
        )

        sessionDataStore.saveSession(
            token = "fake-token-${System.currentTimeMillis()}",
            userId = user.id,
            email = user.email,
            displayName = user.displayName,
        )
        userDao.insertUser(user.toEntity())

        return NetworkResult.Success(user)
    }

    override suspend fun logout() {
        sessionDataStore.clearSession()
        userDao.deleteAll()
    }

    override suspend fun isLoggedIn(): Boolean {
        return sessionDataStore.isLoggedIn()
    }
}
