package com.template.app.data.mapper

import com.template.app.data.local.db.entity.FriendEntity
import com.template.app.domain.model.Friend

fun FriendEntity.toFriend(): Friend = Friend(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    status = status,
)

fun Friend.toEntity(): FriendEntity = FriendEntity(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    status = status,
)
