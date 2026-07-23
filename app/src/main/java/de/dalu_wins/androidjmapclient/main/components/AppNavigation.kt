package de.dalu_wins.androidjmapclient.main.components

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import de.dalu_wins.androidjmapclient.features.email.presentation.EmailScreen
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
    val fadeSpec = MaterialTheme.motionScheme.defaultSpatialSpec<Float>()
    val railOffsetPx = with(LocalDensity.current) {
        if (!isCompact) 80.dp.roundToPx() else 0
    }

    val enterRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? =
        {
            if (isCompact) {
                slideIntoContainer(towards = SlideDirection.Right, animationSpec = bouncySpec)
            } else {
                fadeIn(animationSpec = fadeSpec)
            }
        }
    val enterLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? =
        {
            if (isCompact) {
                slideIntoContainer(
                    towards = SlideDirection.Left,
                    animationSpec = bouncySpec,
                    initialOffset = { fullWidth -> fullWidth - railOffsetPx }
                )
            } else {
                fadeIn(animationSpec = fadeSpec)
            }
        }
    val exitLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? =
        {
            if (isCompact) {
                slideOutOfContainer(
                    towards = SlideDirection.Left,
                    animationSpec = bouncySpec,
                    targetOffset = { fullWidth -> fullWidth - railOffsetPx }
                )
            } else {
                fadeOut(animationSpec = fadeSpec)
            }
        }
    val exitRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? =
        {
            if (isCompact) {
                slideOutOfContainer(towards = SlideDirection.Right, animationSpec = bouncySpec)
            } else {
                fadeOut(animationSpec = fadeSpec)
            }
        }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navigationBarPadding = WindowInsets.navigationBars.asPaddingValues()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.weight(1f)) {
            if (!isCompact) {
                NavigationRail {

                    Spacer(Modifier.weight(1f))

                    NavigationRailItem(
                        selected = currentDestination?.hasRoute<EmailRoute>() == true,
                        onClick = {
                            navController.navigate(EmailRoute) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                    )
                    NavigationRailItem(
                        selected = currentDestination?.hasRoute<SettingsRoute>() == true,
                        onClick = {
                            navController.navigate(SettingsRoute) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    )

                    Spacer(Modifier.weight(1f))

                }
            }

            NavHost(
                navController = navController,
                startDestination = EmailRoute,
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (!isCompact) Modifier.padding(bottom = navigationBarPadding.calculateBottomPadding())
                        else Modifier
                    )
            ) {
                composable<EmailRoute>(
                    enterTransition = enterRight,
                    exitTransition = exitLeft,
                    popEnterTransition = enterRight,
                    popExitTransition = exitLeft,
                ) {
                    EmailScreen(
                        isCompact = isCompact
                    )
                }
                composable<SettingsRoute>(
                    enterTransition = enterLeft,
                    exitTransition = exitRight,
                    popEnterTransition = enterLeft,
                    popExitTransition = exitRight,
                ) {
                    SettingsScreen()
                }
            }
        }

        if (isCompact) {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination?.hasRoute<EmailRoute>() == true,
                    onClick = {
                        navController.navigate(EmailRoute) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                    label = { Text("Email") }
                )
                NavigationBarItem(
                    selected = currentDestination?.hasRoute<SettingsRoute>() == true,
                    onClick = {
                        navController.navigate(SettingsRoute) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        }
    }
}
