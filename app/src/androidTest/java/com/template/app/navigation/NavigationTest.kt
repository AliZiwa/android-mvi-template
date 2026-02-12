package com.template.app.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.template.app.core.navigation.AppNavHost
import com.template.app.core.ui.theme.TemplateAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun appStartsAtLoginScreen() {
        composeTestRule.setContent {
            TemplateAppTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }

        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
    }

    @Test
    fun navigateToRegisterScreen() {
        composeTestRule.setContent {
            TemplateAppTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }

        composeTestRule.onNodeWithText("Don't have an account? Sign Up").performClick()
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
    }
}
