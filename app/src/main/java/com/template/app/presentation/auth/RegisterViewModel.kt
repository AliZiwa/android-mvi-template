package com.template.app.presentation.auth

import androidx.lifecycle.viewModelScope
import com.template.app.core.mvi.MviViewModel
import com.template.app.core.network.NetworkResult
import com.template.app.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : MviViewModel<RegisterContract.State, RegisterContract.Event, RegisterContract.Effect>(
    RegisterContract.State()
) {

    override fun handleEvent(event: RegisterContract.Event) {
        when (event) {
            is RegisterContract.Event.EmailChanged -> {
                setState { copy(email = event.email, errorMessage = null) }
            }
            is RegisterContract.Event.PasswordChanged -> {
                setState { copy(password = event.password, errorMessage = null) }
            }
            is RegisterContract.Event.DisplayNameChanged -> {
                setState { copy(displayName = event.displayName, errorMessage = null) }
            }
            is RegisterContract.Event.RegisterClicked -> register()
            is RegisterContract.Event.LoginClicked -> {
                sendEffect(RegisterContract.Effect.NavigateToLogin)
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            setState { copy(isLoading = true, errorMessage = null) }
            when (val result = registerUseCase(
                currentState.email,
                currentState.password,
                currentState.displayName,
            )) {
                is NetworkResult.Success -> {
                    setState { copy(isLoading = false) }
                    sendEffect(RegisterContract.Effect.NavigateToHome)
                }
                is NetworkResult.Error -> {
                    setState { copy(isLoading = false, errorMessage = result.message) }
                    result.message?.let { sendEffect(RegisterContract.Effect.ShowSnackbar(it)) }
                }
                is NetworkResult.Exception -> {
                    val message = result.throwable.message ?: "Unknown error"
                    setState { copy(isLoading = false, errorMessage = message) }
                    sendEffect(RegisterContract.Effect.ShowSnackbar(message))
                }
            }
        }
    }
}
