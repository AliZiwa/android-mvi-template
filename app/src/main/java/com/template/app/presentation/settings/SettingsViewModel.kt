package com.template.app.presentation.settings

import androidx.lifecycle.viewModelScope
import com.template.app.core.mvi.MviViewModel
import com.template.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : MviViewModel<SettingsContract.State, SettingsContract.Event, SettingsContract.Effect>(
    SettingsContract.State()
) {

    override fun handleEvent(event: SettingsContract.Event) {
        when (event) {
            is SettingsContract.Event.BackClicked -> {
                sendEffect(SettingsContract.Effect.NavigateBack)
            }
            is SettingsContract.Event.LogoutClicked -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            authRepository.logout()
            setState { copy(isLoading = false) }
            sendEffect(SettingsContract.Effect.NavigateToLogin)
        }
    }
}
