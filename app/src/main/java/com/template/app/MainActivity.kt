package com.template.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.template.app.core.navigation.AppNavHost
import com.template.app.core.navigation.DeepLinkHandler
import com.template.app.core.ui.theme.TemplateAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val deepLinkScreen = DeepLinkHandler.parseDeepLink(intent)

        setContent {
            TemplateAppTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    startDestination = deepLinkScreen ?: com.template.app.core.navigation.Screen.Login,
                )
            }
        }
    }
}
