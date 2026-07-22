package de.dalu_wins.androidjmapclient.main.components

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import de.dalu_wins.androidjmapclient.features.email.presentation.EmailNavigationWrapper
import de.dalu_wins.androidjmapclient.features.settings.presentation.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object EmailRoute

@Serializable
object SettingsRoute


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact = !windowSizeClass.isWidthAtLeastBreakpoint(
        WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
    )

    val bouncySpec = MaterialTheme.motionScheme.defaultSpatialSpec<IntOffset>()
    val railOffsetPx = with(LocalDensity.current) {
        if (!isCompact) 80.dp.roundToPx() else 0
    }

    val enterRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> androidx.compose.animation.EnterTransition? =
        {
            slideIntoContainer(towards = SlideDirection.Right, animationSpec = bouncySpec)
        }
    val enterLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> androidx.compose.animation.EnterTransition? =
        {
            slideIntoContainer(
                towards = SlideDirection.Left,
                animationSpec = bouncySpec,
                initialOffset = { fullWidth -> fullWidth - railOffsetPx }
            )
        }
    val exitLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> androidx.compose.animation.ExitTransition? =
        {
            slideOutOfContainer(
                towards = SlideDirection.Left,
                animationSpec = bouncySpec,
                targetOffset = { fullWidth -> fullWidth - railOffsetPx }
            )
        }
    val exitRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> androidx.compose.animation.ExitTransition? =
        {
            slideOutOfContainer(towards = SlideDirection.Right, animationSpec = bouncySpec)
        }

    NavHost(
        navController = navController,
        startDestination = EmailRoute,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<EmailRoute>(
            enterTransition = enterRight,
            exitTransition = exitLeft,
            popEnterTransition = enterRight,
            popExitTransition = exitLeft,
        ) {
            EmailNavigationWrapper(
                isCompact = isCompact,
                onSettingsClick = { navController.navigate(SettingsRoute) }
            )
        }
        composable<SettingsRoute>(
            enterTransition = enterLeft,
            exitTransition = exitRight,
            popEnterTransition = enterLeft,
            popExitTransition = exitRight,
        ) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
