package com.template.app.core.navigation

import android.content.Intent
import android.net.Uri

object DeepLinkHandler {

    private const val SCHEME = "template"
    private const val HOST = "app"

    fun parseDeepLink(intent: Intent): Screen? {
        val uri = intent.data ?: return null
        if (uri.scheme != SCHEME || uri.host != HOST) return null

        return when {
            uri.path == "/home" -> Screen.Home
            uri.path?.startsWith("/friend/") == true -> {
                val friendId = uri.lastPathSegment ?: return null
                Screen.FriendDetail(friendId)
            }
            else -> null
        }
    }

    fun buildDeepLink(screen: Screen): Uri = when (screen) {
        is Screen.Home -> Uri.parse("$SCHEME://$HOST/home")
        is Screen.FriendDetail -> Uri.parse("$SCHEME://$HOST/friend/${screen.friendId}")
        is Screen.Login -> Uri.parse("$SCHEME://$HOST/login")
        is Screen.Register -> Uri.parse("$SCHEME://$HOST/register")
        is Screen.Settings -> Uri.parse("$SCHEME://$HOST/settings")
    }
}
