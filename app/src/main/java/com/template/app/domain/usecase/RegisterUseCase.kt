package com.template.app.domain.usecase

import com.template.app.core.network.NetworkResult
import com.template.app.domain.model.User
import com.template.app.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        displayName: String,
    ): NetworkResult<User> {
        if (email.isBlank()) return NetworkResult.Error(message = "Email cannot be empty")
        if (password.length < 6) return NetworkResult.Error(message = "Password must be at least 6 characters")
        if (displayName.isBlank()) return NetworkResult.Error(message = "Display name cannot be empty")
        return authRepository.register(email, password, displayName)
    }
}
