package com.template.app.presentation.friends.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.template.app.core.mvi.MviViewModel
import com.template.app.core.navigation.Screen
import com.template.app.core.network.NetworkResult
import com.template.app.domain.usecase.GetFriendDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFriendDetailUseCase: GetFriendDetailUseCase,
) : MviViewModel<FriendDetailContract.State, FriendDetailContract.Event, FriendDetailContract.Effect>(
    FriendDetailContract.State()
) {

    private val route = savedStateHandle.toRoute<Screen.FriendDetail>()

    init {
        loadFriend()
    }

    override fun handleEvent(event: FriendDetailContract.Event) {
        when (event) {
            is FriendDetailContract.Event.BackClicked -> {
                sendEffect(FriendDetailContract.Effect.NavigateBack)
            }
        }
    }

    private fun loadFriend() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            when (val result = getFriendDetailUseCase(route.friendId)) {
                is NetworkResult.Success -> {
                    setState { copy(friend = result.data, isLoading = false) }
                }
                is NetworkResult.Error -> {
                    setState { copy(isLoading = false, errorMessage = result.message) }
                    result.message?.let { sendEffect(FriendDetailContract.Effect.ShowSnackbar(it)) }
                }
                is NetworkResult.Exception -> {
                    val message = result.throwable.message ?: "Unknown error"
                    setState { copy(isLoading = false, errorMessage = message) }
                    sendEffect(FriendDetailContract.Effect.ShowSnackbar(message))
                }
            }
        }
    }
}
