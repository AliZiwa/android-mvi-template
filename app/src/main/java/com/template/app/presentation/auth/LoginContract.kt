package com.template.app.presentation.auth

import com.template.app.core.mvi.UiEffect
import com.template.app.core.mvi.UiEvent
import com.template.app.core.mvi.UiState

object LoginContract {

    data class State(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
    ) : UiState

    sealed class Event : UiEvent {
        data class EmailChanged(val email: String) : Event()
        data class PasswordChanged(val password: String) : Event()
        data object LoginClicked : Event()
        data object RegisterClicked : Event()
    }

    sealed class Effect : UiEffect {
        data object NavigateToHome : Effect()
        data object NavigateToRegister : Effect()
        data class ShowSnackbar(val message: String) : Effect()
    }
}
