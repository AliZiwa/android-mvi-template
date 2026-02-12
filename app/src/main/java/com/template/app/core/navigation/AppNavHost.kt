package com.template.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.template.app.presentation.auth.LoginScreen
import com.template.app.presentation.auth.RegisterScreen
import com.template.app.presentation.friends.detail.FriendDetailScreen
import com.template.app.presentation.home.HomeScreen
import com.template.app.presentation.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Screen = Screen.Login,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Screen.Login> {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                },
            )
        }

        composable<Screen.Register> {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                },
            )
        }

        composable<Screen.Home> {
            HomeScreen(
                onNavigateToFriendDetail = { friendId ->
                    navController.navigate(Screen.FriendDetail(friendId))
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings)
                },
            )
        }

        composable<Screen.FriendDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.FriendDetail>()
            FriendDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        composable<Screen.Settings> {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    navController.navigate(Screen.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }
    }
}
