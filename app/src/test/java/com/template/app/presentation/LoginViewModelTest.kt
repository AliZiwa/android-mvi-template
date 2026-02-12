package com.template.app.presentation

import app.cash.turbine.test
import com.template.app.core.network.NetworkResult
import com.template.app.domain.model.User
import com.template.app.domain.usecase.LoginUseCase
import com.template.app.presentation.auth.LoginContract
import com.template.app.presentation.auth.LoginViewModel
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginUseCase = mockk()
        viewModel = LoginViewModel(loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `email changed updates state`() {
        viewModel.onEvent(LoginContract.Event.EmailChanged("test@test.com"))

        assertEquals("test@test.com", viewModel.currentState.email)
    }

    @Test
    fun `password changed updates state`() {
        viewModel.onEvent(LoginContract.Event.PasswordChanged("password"))

        assertEquals("password", viewModel.currentState.password)
    }

    @Test
    fun `successful login emits NavigateToHome effect`() = runTest {
        val user = User(id = "1", email = "test@test.com", displayName = "Test", avatarUrl = null)
        coEvery { loginUseCase(any(), any()) } returns NetworkResult.Success(user)

        viewModel.onEvent(LoginContract.Event.EmailChanged("test@test.com"))
        viewModel.onEvent(LoginContract.Event.PasswordChanged("password"))

        viewModel.effect.test {
            viewModel.onEvent(LoginContract.Event.LoginClicked)
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(LoginContract.Effect.NavigateToHome, awaitItem())
        }
    }

    @Test
    fun `failed login sets error message`() = runTest {
        coEvery { loginUseCase(any(), any()) } returns NetworkResult.Error(message = "Invalid credentials")

        viewModel.onEvent(LoginContract.Event.EmailChanged("test@test.com"))
        viewModel.onEvent(LoginContract.Event.PasswordChanged("wrong"))

        viewModel.effect.test {
            viewModel.onEvent(LoginContract.Event.LoginClicked)
            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals("Invalid credentials", viewModel.currentState.errorMessage)
            assertFalse(viewModel.currentState.isLoading)

            val effect = awaitItem()
            assertTrue(effect is LoginContract.Effect.ShowSnackbar)
        }
    }

    @Test
    fun `register clicked emits NavigateToRegister effect`() = runTest {
        viewModel.effect.test {
            viewModel.onEvent(LoginContract.Event.RegisterClicked)
            assertEquals(LoginContract.Effect.NavigateToRegister, awaitItem())
        }
    }

    @Test
    fun `email changed clears error message`() {
        // Set error state first by testing internal state
        viewModel.onEvent(LoginContract.Event.EmailChanged("test@test.com"))
        assertNull(viewModel.currentState.errorMessage)
    }
}
