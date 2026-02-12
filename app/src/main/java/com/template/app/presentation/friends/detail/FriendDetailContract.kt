package com.template.app.presentation.friends.detail

import com.template.app.core.mvi.UiEffect
import com.template.app.core.mvi.UiEvent
import com.template.app.core.mvi.UiState
import com.template.app.domain.model.Friend

object FriendDetailContract {

    data class State(
        val friend: Friend? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
    ) : UiState

    sealed class Event : UiEvent {
        data object BackClicked : Event()
    }

    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data class ShowSnackbar(val message: String) : Effect()
    }
}
