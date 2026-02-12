package com.template.app.core.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Login : Screen

    @Serializable
    data object Register : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data class FriendDetail(val friendId: String) : Screen

    @Serializable
    data object Settings : Screen
}
