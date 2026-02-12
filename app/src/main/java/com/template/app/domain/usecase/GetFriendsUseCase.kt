package com.template.app.domain.usecase

import com.template.app.core.network.NetworkResult
import com.template.app.domain.model.Friend
import com.template.app.domain.repository.FriendRepository
import javax.inject.Inject

class GetFriendsUseCase @Inject constructor(
    private val friendRepository: FriendRepository,
) {
    suspend operator fun invoke(): NetworkResult<List<Friend>> {
        return friendRepository.getFriends()
    }
}
