package com.template.app.domain.usecase

import com.template.app.core.network.NetworkResult
import com.template.app.domain.model.User
import com.template.app.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): NetworkResult<User> {
        if (email.isBlank()) return NetworkResult.Error(message = "Email cannot be empty")
        if (password.isBlank()) return NetworkResult.Error(message = "Password cannot be empty")
        return authRepository.login(email, password)
    }
}
