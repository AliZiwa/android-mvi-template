package com.template.app.domain.repository

import com.template.app.core.network.NetworkResult
import com.template.app.domain.model.User

interface UserRepository {
    suspend fun getUser(): NetworkResult<User>
    suspend fun getCachedUser(): User?
}
