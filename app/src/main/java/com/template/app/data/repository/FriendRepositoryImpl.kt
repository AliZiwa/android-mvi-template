package com.template.app.data.repository

import com.template.app.core.network.NetworkResult
import com.template.app.data.local.db.dao.FriendDao
import com.template.app.data.mapper.toEntity
import com.template.app.data.mapper.toFriend
import com.template.app.domain.model.Friend
import com.template.app.domain.repository.FriendRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendDao: FriendDao,
) : FriendRepository {

    private val fakeFriends = listOf(
        Friend(id = "f1", name = "Alice Johnson", avatarUrl = null, status = "Online"),
        Friend(id = "f2", name = "Bob Smith", avatarUrl = null, status = "Away"),
        Friend(id = "f3", name = "Carol Williams", avatarUrl = null, status = "Offline"),
        Friend(id = "f4", name = "Dave Brown", avatarUrl = null, status = "Online"),
        Friend(id = "f5", name = "Eve Davis", avatarUrl = null, status = "Online"),
    )

    override suspend fun getFriends(): NetworkResult<List<Friend>> {
        delay(800)

        // Cache friends locally
        friendDao.insertFriends(fakeFriends.map { it.toEntity() })

        return NetworkResult.Success(fakeFriends)
    }

    override suspend fun getFriendDetail(friendId: String): NetworkResult<Friend> {
        delay(500)

        val friend = fakeFriends.find { it.id == friendId }
            ?: friendDao.getFriendById(friendId)?.toFriend()

        return if (friend != null) {
            NetworkResult.Success(friend)
        } else {
            NetworkResult.Error(message = "Friend not found")
        }
    }
}
