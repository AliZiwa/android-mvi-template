package com.template.app.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.template.app.core.ui.theme.TemplateAppTheme
import com.template.app.presentation.home.HomeScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun homeScreen_displaysTitle() {
        composeTestRule.setContent {
            TemplateAppTheme {
                HomeScreen(
                    onNavigateToFriendDetail = {},
                    onNavigateToSettings = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysFriendsHeader() {
        composeTestRule.setContent {
            TemplateAppTheme {
                HomeScreen(
                    onNavigateToFriendDetail = {},
                    onNavigateToSettings = {},
                )
            }
        }

        composeTestRule.waitForIdle()
        // Friends header should appear after loading
        composeTestRule.onNodeWithText("Friends").assertIsDisplayed()
    }
}
