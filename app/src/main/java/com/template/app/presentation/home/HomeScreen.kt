package com.template.app.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.template.app.domain.model.Friend
import com.template.app.presentation.components.FriendListItem
import com.template.app.presentation.components.LoadingIndicator
import com.template.app.presentation.components.RoundedProfileImage
import com.template.app.presentation.friends.FriendProfileBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToFriendDetail: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedFriend by remember { mutableStateOf<Friend?>(null) }
    val bottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.NavigateToFriendDetail -> {
                    onNavigateToFriendDetail(effect.friendId)
                }
                is HomeContract.Effect.NavigateToSettings -> onNavigateToSettings()
                is HomeContract.Effect.ShowFriendBottomSheet -> {
                    selectedFriend = effect.friend
                }
                is HomeContract.Effect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    if (selectedFriend != null) {
        ModalBottomSheet(
            onDismissRequest = {
                selectedFriend = null
                viewModel.onEvent(HomeContract.Event.DismissBottomSheet)
            },
            sheetState = bottomSheetState,
        ) {
            FriendProfileBottomSheet(friend = selectedFriend!!)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(HomeContract.Event.SettingsClicked) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
            )
        },
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                LoadingIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                // User profile section
                state.user?.let { user ->
                    item {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            RoundedProfileImage(
                                imageUrl = user.avatarUrl,
                                size = 80,
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = user.displayName,
                                style = MaterialTheme.typography.titleLarge,
                            )
                            Text(
                                text = user.email,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }

                // Friends section header
                item {
                    Text(
                        text = "Friends",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                    )
                }

                // Friends list
                items(state.friends, key = { it.id }) { friend ->
                    FriendListItem(
                        friend = friend,
                        onClick = { viewModel.onEvent(HomeContract.Event.FriendClicked(friend.id)) },
                        onLongClick = { viewModel.onEvent(HomeContract.Event.FriendLongPressed(friend)) },
                    )
                }
            }
        }
    }
}
