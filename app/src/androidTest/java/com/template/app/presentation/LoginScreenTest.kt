package com.template.app.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.template.app.core.ui.theme.TemplateAppTheme
import com.template.app.presentation.auth.LoginScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun loginScreen_displaysTitle() {
        composeTestRule.setContent {
            TemplateAppTheme {
                LoginScreen(
                    onNavigateToRegister = {},
                    onNavigateToHome = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
    }

    @Test
    fun loginScreen_displaysSignInButton() {
        composeTestRule.setContent {
            TemplateAppTheme {
                LoginScreen(
                    onNavigateToRegister = {},
                    onNavigateToHome = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Sign In").assertIsDisplayed()
    }

    @Test
    fun loginScreen_displaysSignUpLink() {
        composeTestRule.setContent {
            TemplateAppTheme {
                LoginScreen(
                    onNavigateToRegister = {},
                    onNavigateToHome = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Don't have an account? Sign Up").assertIsDisplayed()
    }
}
