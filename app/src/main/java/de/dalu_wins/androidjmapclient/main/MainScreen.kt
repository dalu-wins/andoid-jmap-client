package de.dalu_wins.androidjmapclient.main

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import de.dalu_wins.androidjmapclient.features.settings.domain.AppTheme
import de.dalu_wins.androidjmapclient.main.components.AndroidJMAPClientTheme
import de.dalu_wins.androidjmapclient.main.components.AppNavigation

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    deactivateSplashScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is MainUiState.Success) {
            deactivateSplashScreen()
        }
    }

    when (val state = uiState) {
        is MainUiState.Loading -> Unit
        is MainUiState.Success -> {
            val settings = state.settings
            val isDarkTheme = when (settings.theme) {
                AppTheme.LIGHT -> false
                AppTheme.DARK -> true
                AppTheme.SYSTEM -> isSystemInDarkTheme()
            }

            AndroidJMAPClientTheme(
                darkTheme = isDarkTheme,
                dynamicColor = settings.dynamicColors
            ) {

                val view = LocalView.current
                val backgroundColor = MaterialTheme.colorScheme.background
                if (!view.isInEditMode) {
                    SideEffect {
                        val window = (view.context as Activity).window
                        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                            !isDarkTheme
                        WindowCompat.getInsetsController(
                            window,
                            view
                        ).isAppearanceLightNavigationBars =
                            !isDarkTheme
                        window.setBackgroundDrawable(
                            backgroundColor.toArgb().toDrawable()
                        ) // Necessary for correct animations when overriding dark mode in-app
                    }
                }

                AppNavigation()
            }
        }
    }
}