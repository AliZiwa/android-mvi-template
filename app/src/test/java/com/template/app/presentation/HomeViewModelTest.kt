package com.template.app.presentation

import app.cash.turbine.test
import com.template.app.core.network.NetworkResult
import com.template.app.domain.model.Friend
import com.template.app.domain.model.User
import com.template.app.domain.usecase.GetFriendsUseCase
import com.template.app.domain.usecase.GetUserUseCase
import com.template.app.presentation.home.HomeContract
import com.template.app.presentation.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var getFriendsUseCase: GetFriendsUseCase
    private lateinit var viewModel: HomeViewModel

    private val testUser = User(id = "1", email = "test@test.com", displayName = "Test", avatarUrl = null)
    private val testFriends = listOf(
        Friend(id = "f1", name = "Alice", avatarUrl = null, status = "Online"),
        Friend(id = "f2", name = "Bob", avatarUrl = null, status = "Away"),
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getUserUseCase = mockk()
        getFriendsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): HomeViewModel {
        return HomeViewModel(getUserUseCase, getFriendsUseCase)
    }

    @Test
    fun `init loads user and friends`() = runTest {
        coEvery { getUserUseCase() } returns NetworkResult.Success(testUser)
        coEvery { getFriendsUseCase() } returns NetworkResult.Success(testFriends)

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(testUser, viewModel.currentState.user)
        assertEquals(testFriends, viewModel.currentState.friends)
        assertEquals(false, viewModel.currentState.isLoading)
    }

    @Test
    fun `friend clicked emits NavigateToFriendDetail effect`() = runTest {
        coEvery { getUserUseCase() } returns NetworkResult.Success(testUser)
        coEvery { getFriendsUseCase() } returns NetworkResult.Success(testFriends)

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.effect.test {
            viewModel.onEvent(HomeContract.Event.FriendClicked("f1"))
            val effect = awaitItem()
            assertTrue(effect is HomeContract.Effect.NavigateToFriendDetail)
            assertEquals("f1", (effect as HomeContract.Effect.NavigateToFriendDetail).friendId)
        }
    }

    @Test
    fun `settings clicked emits NavigateToSettings effect`() = runTest {
        coEvery { getUserUseCase() } returns NetworkResult.Success(testUser)
        coEvery { getFriendsUseCase() } returns NetworkResult.Success(testFriends)

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.effect.test {
            viewModel.onEvent(HomeContract.Event.SettingsClicked)
            assertEquals(HomeContract.Effect.NavigateToSettings, awaitItem())
        }
    }

    @Test
    fun `friends load error sets error message`() = runTest {
        coEvery { getUserUseCase() } returns NetworkResult.Success(testUser)
        coEvery { getFriendsUseCase() } returns NetworkResult.Error(message = "Network error")

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Network error", viewModel.currentState.errorMessage)
        assertEquals(false, viewModel.currentState.isLoading)
    }
}
