package com.template.app.data.mapper

import com.template.app.data.local.db.entity.UserEntity
import com.template.app.data.remote.dto.LoginResponse
import com.template.app.domain.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {

    @Test
    fun `LoginResponse toUser maps correctly`() {
        val response = LoginResponse(
            userId = "1",
            email = "test@test.com",
            displayName = "Test User",
            avatarUrl = "https://example.com/avatar.jpg",
            token = "token-123",
        )

        val user = response.toUser()

        assertEquals("1", user.id)
        assertEquals("test@test.com", user.email)
        assertEquals("Test User", user.displayName)
        assertEquals("https://example.com/avatar.jpg", user.avatarUrl)
    }

    @Test
    fun `UserEntity toUser maps correctly`() {
        val entity = UserEntity(
            id = "1",
            email = "test@test.com",
            displayName = "Test User",
            avatarUrl = null,
        )

        val user = entity.toUser()

        assertEquals("1", user.id)
        assertEquals("test@test.com", user.email)
        assertEquals("Test User", user.displayName)
        assertEquals(null, user.avatarUrl)
    }

    @Test
    fun `User toEntity maps correctly`() {
        val user = User(
            id = "1",
            email = "test@test.com",
            displayName = "Test User",
            avatarUrl = "https://example.com/avatar.jpg",
        )

        val entity = user.toEntity()

        assertEquals("1", entity.id)
        assertEquals("test@test.com", entity.email)
        assertEquals("Test User", entity.displayName)
        assertEquals("https://example.com/avatar.jpg", entity.avatarUrl)
    }
}
