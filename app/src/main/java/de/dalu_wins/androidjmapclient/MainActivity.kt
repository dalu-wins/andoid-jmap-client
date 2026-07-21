package de.dalu_wins.androidjmapclient

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import de.dalu_wins.androidjmapclient.main.MainScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var keepSplash by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { keepSplash }

        transparentSystemUI()

        setContent {
            MainScreen(
                deactivateSplashScreen = { keepSplash = false }
            )
        }
    }

    private fun transparentSystemUI() {
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isDarkTheme = (nightModeFlags == Configuration.UI_MODE_NIGHT_YES)

        val lightTransparentStyle =
            SystemBarStyle.light(scrim = Color.TRANSPARENT, darkScrim = Color.TRANSPARENT)
        val darkTransparentStyle = SystemBarStyle.dark(scrim = Color.TRANSPARENT)
        val systemBarStyle = if (isDarkTheme) darkTransparentStyle else lightTransparentStyle

        enableEdgeToEdge(statusBarStyle = systemBarStyle, navigationBarStyle = systemBarStyle)
    }
}