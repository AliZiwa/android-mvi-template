package com.template.app.domain.usecase

import com.template.app.core.network.NetworkResult
import com.template.app.domain.model.User
import com.template.app.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): NetworkResult<User> {
        return userRepository.getUser()
    }
}
