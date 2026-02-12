package com.template.app.domain.usecase

import com.template.app.core.network.NetworkResult
import com.template.app.domain.model.User
import com.template.app.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        authRepository = mockk()
        loginUseCase = LoginUseCase(authRepository)
    }

    @Test
    fun `invoke with valid credentials returns success`() = runTest {
        val user = User(id = "1", email = "test@test.com", displayName = "Test", avatarUrl = null)
        coEvery { authRepository.login("test@test.com", "password") } returns NetworkResult.Success(user)

        val result = loginUseCase("test@test.com", "password")

        assertTrue(result is NetworkResult.Success)
        assertEquals(user, (result as NetworkResult.Success).data)
    }

    @Test
    fun `invoke with blank email returns error`() = runTest {
        val result = loginUseCase("", "password")

        assertTrue(result is NetworkResult.Error)
        assertEquals("Email cannot be empty", (result as NetworkResult.Error).message)
    }

    @Test
    fun `invoke with blank password returns error`() = runTest {
        val result = loginUseCase("test@test.com", "")

        assertTrue(result is NetworkResult.Error)
        assertEquals("Password cannot be empty", (result as NetworkResult.Error).message)
    }

    @Test
    fun `invoke with invalid credentials returns error from repository`() = runTest {
        coEvery { authRepository.login("test@test.com", "wrong") } returns
            NetworkResult.Error(code = 401, message = "Invalid credentials")

        val result = loginUseCase("test@test.com", "wrong")

        assertTrue(result is NetworkResult.Error)
        assertEquals("Invalid credentials", (result as NetworkResult.Error).message)
    }
}
