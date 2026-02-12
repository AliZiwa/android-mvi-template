package com.template.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val userId: String,
    val email: String,
    val displayName: String,
    val avatarUrl: String? = null,
    val token: String,
)
