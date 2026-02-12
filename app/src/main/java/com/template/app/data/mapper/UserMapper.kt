package com.template.app.data.mapper

import com.template.app.data.local.db.entity.UserEntity
import com.template.app.data.remote.dto.LoginResponse
import com.template.app.domain.model.User

fun LoginResponse.toUser(): User = User(
    id = userId,
    email = email,
    displayName = displayName,
    avatarUrl = avatarUrl,
)

fun UserEntity.toUser(): User = User(
    id = id,
    email = email,
    displayName = displayName,
    avatarUrl = avatarUrl,
)

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    email = email,
    displayName = displayName,
    avatarUrl = avatarUrl,
)
