package com.template.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Friend(
    val id: String,
    val name: String,
    val avatarUrl: String?,
    val status: String,
) : Parcelable
