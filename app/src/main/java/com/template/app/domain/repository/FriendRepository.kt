package com.template.app.domain.repository

import com.template.app.core.network.NetworkResult
import com.template.app.domain.model.Friend

interface FriendRepository {
    suspend fun getFriends(): NetworkResult<List<Friend>>
    suspend fun getFriendDetail(friendId: String): NetworkResult<Friend>
}
