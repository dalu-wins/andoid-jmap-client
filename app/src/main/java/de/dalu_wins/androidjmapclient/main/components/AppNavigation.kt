package de.dalu_wins.androidjmapclient.main.components

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.ReportGmailerrorred
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import de.dalu_wins.androidjmapclient.R
import de.dalu_wins.androidjmapclient.features.email.presentation.EmailScreen
import de.dalu_wins.androidjmapclient.features.settings.presentation.SettingsScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object EmailRoute

@Serializable
object SettingsRoute

private data class NavItem(
    val icon: ImageVector,
    val label: String
)

private val navItems = listOf(
    NavItem(Icons.Default.Inbox, "Inbox"),
    NavItem(Icons.Default.Draw, "Concepts"),
    NavItem(Icons.AutoMirrored.Filled.Send, "Sent"),
    NavItem(Icons.Default.Star, "Marked"),
    NavItem(Icons.Default.ReportGmailerrorred, "Spam"),
    NavItem(Icons.Default.Archive, "Archived"),
    NavItem(Icons.Filled.Delete, "Trash"),
    NavItem(Icons.Default.AllInbox, "All")
)

object AppNavigationDefaults {
    val DRAWER_TITLE_HORIZONTAL_PADDING = 16.dp
    val DRAWER_TITLE_VERTICAL_PADDING = 16.dp
    val DRAWER_COLUMN_VERTICAL_PADDING = 8.dp
}

@Composable
fun AppNavigation(
    drawerTitleHorizontalPadding: Dp = AppNavigationDefaults.DRAWER_TITLE_HORIZONTAL_PADDING,
    drawerTitleVerticalPadding: Dp = AppNavigationDefaults.DRAWER_TITLE_VERTICAL_PADDING,
    columnVerticalPadding: Dp = AppNavigationDefaults.DRAWER_COLUMN_VERTICAL_PADDING
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableIntStateOf(0) }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact = !windowSizeClass.isWidthAtLeastBreakpoint(
        WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
    )

    val bouncySpec = MaterialTheme.motionScheme.defaultSpatialSpec<IntOffset>()
    val railOffsetPx = with(LocalDensity.current) {
        if (!isCompact) 80.dp.roundToPx() else 0
    }

    // Separate Funktionen für Enter/Exit, da slideIntoContainer/slideOutOfContainer
    // unterschiedliche Typen (EnterTransition/ExitTransition) zurückgeben
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

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isCompact,
        drawerContent = {
            if (isCompact) {
                ModalDrawerSheet {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(
                                vertical = drawerTitleVerticalPadding,
                                horizontal = drawerTitleHorizontalPadding
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "App Icon",
                            modifier = Modifier.width(64.dp)
                        )
                        Text(
                            text = "mail",
                            style = MaterialTheme.typography.titleMediumEmphasized,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            scope.launch { drawerState.close() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Drawer"
                            )
                        }
                    }
                    HorizontalDivider()
                    Column(modifier = Modifier.padding(vertical = columnVerticalPadding)) {
                        navItems.forEachIndexed { index, item ->
                            NavigationDrawerItem(
                                icon = { Icon(item.icon, contentDescription = item.label) },
                                label = { Text(item.label) },
                                selected = selectedItem == index,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                        selectedItem = index
                                    }
                                },
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                            )
                        }
                    }
                }
            }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (!isCompact) {
                NavigationRail(
                    modifier = Modifier.zIndex(2f),
                    header = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "App Icon",
                            modifier = Modifier.width(64.dp)
                        )
                    }
                ) {
                    navItems.forEachIndexed { index, item ->
                        NavigationRailItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }
            }

            NavHost(
                navController = navController,
                startDestination = EmailRoute,
                modifier = Modifier.weight(1f)
            ) {
                composable<EmailRoute>(
                    enterTransition = enterRight,
                    exitTransition = exitLeft,
                    popEnterTransition = enterRight,
                    popExitTransition = exitLeft,
                ) {
                    EmailScreen(
                        showDrawer = isCompact,
                        onSettingsClick = { navController.navigate(SettingsRoute) },
                        onOpenDrawer = {
                            if (isCompact) scope.launch { drawerState.open() }
                        }
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
    }
}