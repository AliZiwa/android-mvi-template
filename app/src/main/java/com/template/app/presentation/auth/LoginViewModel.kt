package com.template.app.presentation.auth

import androidx.lifecycle.viewModelScope
import com.template.app.core.mvi.MviViewModel
import com.template.app.core.network.NetworkResult
import com.template.app.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : MviViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>(
    LoginContract.State()
) {

    override fun handleEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.EmailChanged -> {
                setState { copy(email = event.email, errorMessage = null) }
            }
            is LoginContract.Event.PasswordChanged -> {
                setState { copy(password = event.password, errorMessage = null) }
            }
            is LoginContract.Event.LoginClicked -> login()
            is LoginContract.Event.RegisterClicked -> {
                sendEffect(LoginContract.Effect.NavigateToRegister)
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            setState { copy(isLoading = true, errorMessage = null) }
            when (val result = loginUseCase(currentState.email, currentState.password)) {
                is NetworkResult.Success -> {
                    setState { copy(isLoading = false) }
                    sendEffect(LoginContract.Effect.NavigateToHome)
                }
                is NetworkResult.Error -> {
                    setState { copy(isLoading = false, errorMessage = result.message) }
                    result.message?.let { sendEffect(LoginContract.Effect.ShowSnackbar(it)) }
                }
                is NetworkResult.Exception -> {
                    val message = result.throwable.message ?: "Unknown error"
                    setState { copy(isLoading = false, errorMessage = message) }
                    sendEffect(LoginContract.Effect.ShowSnackbar(message))
                }
            }
        }
    }
}
