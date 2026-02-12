package com.template.app.domain.repository

import com.template.app.core.network.NetworkResult
import com.template.app.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): NetworkResult<User>
    suspend fun register(email: String, password: String, displayName: String): NetworkResult<User>
    suspend fun logout()
    suspend fun isLoggedIn(): Boolean
}
