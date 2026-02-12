package com.template.app.presentation.home

import androidx.lifecycle.viewModelScope
import com.template.app.core.mvi.MviViewModel
import com.template.app.core.network.NetworkResult
import com.template.app.domain.usecase.GetFriendsUseCase
import com.template.app.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getFriendsUseCase: GetFriendsUseCase,
) : MviViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>(
    HomeContract.State()
) {

    init {
        handleEvent(HomeContract.Event.LoadData)
    }

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.LoadData -> loadData()
            is HomeContract.Event.FriendClicked -> {
                sendEffect(HomeContract.Effect.NavigateToFriendDetail(event.friendId))
            }
            is HomeContract.Event.FriendLongPressed -> {
                sendEffect(HomeContract.Effect.ShowFriendBottomSheet(event.friend))
            }
            is HomeContract.Event.SettingsClicked -> {
                sendEffect(HomeContract.Effect.NavigateToSettings)
            }
            is HomeContract.Event.DismissBottomSheet -> {}
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }

            when (val userResult = getUserUseCase()) {
                is NetworkResult.Success -> setState { copy(user = userResult.data) }
                is NetworkResult.Error -> {}
                is NetworkResult.Exception -> {}
            }

            when (val friendsResult = getFriendsUseCase()) {
                is NetworkResult.Success -> {
                    setState { copy(friends = friendsResult.data, isLoading = false) }
                }
                is NetworkResult.Error -> {
                    setState { copy(isLoading = false, errorMessage = friendsResult.message) }
                    friendsResult.message?.let { sendEffect(HomeContract.Effect.ShowSnackbar(it)) }
                }
                is NetworkResult.Exception -> {
                    val message = friendsResult.throwable.message ?: "Unknown error"
                    setState { copy(isLoading = false, errorMessage = message) }
                    sendEffect(HomeContract.Effect.ShowSnackbar(message))
                }
            }
        }
    }
}
