package com.template.app.data.repository

import com.template.app.core.network.NetworkResult
import com.template.app.data.local.datastore.SessionDataStore
import com.template.app.data.local.db.dao.UserDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthRepositoryImplTest {

    private lateinit var sessionDataStore: SessionDataStore
    private lateinit var userDao: UserDao
    private lateinit var repository: AuthRepositoryImpl

    @Before
    fun setUp() {
        sessionDataStore = mockk()
        userDao = mockk()
        repository = AuthRepositoryImpl(sessionDataStore, userDao)
    }

    @Test
    fun `login with valid email returns success`() = runTest {
        coEvery { sessionDataStore.saveSession(any(), any(), any(), any()) } just runs
        coEvery { userDao.insertUser(any()) } just runs

        val result = repository.login("user@test.com", "password")

        assertTrue(result is NetworkResult.Success)
        assertEquals("user@test.com", (result as NetworkResult.Success).data.email)
        coVerify { sessionDataStore.saveSession(any(), any(), any(), any()) }
        coVerify { userDao.insertUser(any()) }
    }

    @Test
    fun `login with error email returns error`() = runTest {
        val result = repository.login("error@test.com", "password")

        assertTrue(result is NetworkResult.Error)
        assertEquals(401, (result as NetworkResult.Error).code)
    }

    @Test
    fun `logout clears session and user data`() = runTest {
        coEvery { sessionDataStore.clearSession() } just runs
        coEvery { userDao.deleteAll() } just runs

        repository.logout()

        coVerify { sessionDataStore.clearSession() }
        coVerify { userDao.deleteAll() }
    }

    @Test
    fun `isLoggedIn delegates to session data store`() = runTest {
        coEvery { sessionDataStore.isLoggedIn() } returns true

        val result = repository.isLoggedIn()

        assertTrue(result)
        coVerify { sessionDataStore.isLoggedIn() }
    }
}
