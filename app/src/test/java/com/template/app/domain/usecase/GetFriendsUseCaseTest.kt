package com.template.app.domain.usecase

import com.template.app.core.network.NetworkResult
import com.template.app.domain.model.Friend
import com.template.app.domain.repository.FriendRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetFriendsUseCaseTest {

    private lateinit var friendRepository: FriendRepository
    private lateinit var getFriendsUseCase: GetFriendsUseCase

    @Before
    fun setUp() {
        friendRepository = mockk()
        getFriendsUseCase = GetFriendsUseCase(friendRepository)
    }

    @Test
    fun `invoke returns friends list on success`() = runTest {
        val friends = listOf(
            Friend(id = "1", name = "Alice", avatarUrl = null, status = "Online"),
            Friend(id = "2", name = "Bob", avatarUrl = null, status = "Away"),
        )
        coEvery { friendRepository.getFriends() } returns NetworkResult.Success(friends)

        val result = getFriendsUseCase()

        assertTrue(result is NetworkResult.Success)
        assertEquals(friends, (result as NetworkResult.Success).data)
    }

    @Test
    fun `invoke returns error on failure`() = runTest {
        coEvery { friendRepository.getFriends() } returns NetworkResult.Error(message = "Network error")

        val result = getFriendsUseCase()

        assertTrue(result is NetworkResult.Error)
        assertEquals("Network error", (result as NetworkResult.Error).message)
    }
}
