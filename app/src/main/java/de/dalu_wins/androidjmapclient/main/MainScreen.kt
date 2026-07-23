package de.dalu_wins.androidjmapclient.main

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.WindowCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import de.dalu_wins.androidjmapclient.features.settings.domain.AppTheme
import de.dalu_wins.androidjmapclient.main.components.AndroidJMAPClientTheme
import de.dalu_wins.androidjmapclient.main.components.AppNavigation

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    deactivateSplashScreen: () -> Unit
) {
    val mainUiState by viewModel.uiState.collectAsState()

    LaunchedEffect(mainUiState) {
        if (mainUiState is MainUiState.Success) {
            deactivateSplashScreen()
        }
    }

    val settings = (mainUiState as? MainUiState.Success)?.settings
    val isDarkTheme = when (settings?.theme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
        AppTheme.SYSTEM, null -> isSystemInDarkTheme()
    }

    AndroidJMAPClientTheme(
        darkTheme = isDarkTheme,
        dynamicColor = settings?.dynamicColors ?: true
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val view = LocalView.current
            if (!view.isInEditMode) {
                val backgroundColor = MaterialTheme.colorScheme.background
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

            when (mainUiState) {
                is MainUiState.Loading -> Unit
                is MainUiState.Success -> {
                    AppNavigation()
                }
            }
        }
    }
}
