package com.template.app.presentation.settings

import com.template.app.core.mvi.UiEffect
import com.template.app.core.mvi.UiEvent
import com.template.app.core.mvi.UiState

object SettingsContract {

    data class State(
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent {
        data object BackClicked : Event()
        data object LogoutClicked : Event()
    }

    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object NavigateToLogin : Effect()
        data class ShowSnackbar(val message: String) : Effect()
    }
}
