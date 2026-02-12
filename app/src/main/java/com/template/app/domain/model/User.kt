package com.template.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val email: String,
    val displayName: String,
    val avatarUrl: String?,
) : Parcelable
