package com.template.app.presentation.home

import com.template.app.core.mvi.UiEffect
import com.template.app.core.mvi.UiEvent
import com.template.app.core.mvi.UiState
import com.template.app.domain.model.Friend
import com.template.app.domain.model.User

object HomeContract {

    data class State(
        val user: User? = null,
        val friends: List<Friend> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
    ) : UiState

    sealed class Event : UiEvent {
        data object LoadData : Event()
        data class FriendClicked(val friendId: String) : Event()
        data class FriendLongPressed(val friend: Friend) : Event()
        data object SettingsClicked : Event()
        data object DismissBottomSheet : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateToFriendDetail(val friendId: String) : Effect()
        data object NavigateToSettings : Effect()
        data class ShowFriendBottomSheet(val friend: Friend) : Effect()
        data class ShowSnackbar(val message: String) : Effect()
    }
}
