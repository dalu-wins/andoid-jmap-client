package de.dalu_wins.androidjmapclient.features.email.presentation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.dalu_wins.androidjmapclient.features.email.domain.Email
import de.dalu_wins.androidjmapclient.features.email.presentation.components.EmailDetail
import de.dalu_wins.androidjmapclient.features.email.presentation.components.EmailList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EmailScreen(
    showDrawer: Boolean,
    onOpenDrawer: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EmailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val customDirective = calculatePaneScaffoldDirective(adaptiveInfo).copy(
        horizontalPartitionSpacerSize = 0.dp
    ) // Override to avoid huge gap between panes in tablet mode
    val navigator = rememberListDetailPaneScaffoldNavigator<Email>(
        scaffoldDirective = customDirective
    )
    val scope = rememberCoroutineScope()

    var lastSelectedEmail by remember { mutableStateOf<Email?>(state.emails.firstOrNull()) }
    val currentEmail = navigator.currentDestination?.contentKey

    if (currentEmail != null) {
        lastSelectedEmail = currentEmail
    }

    val bouncyScaleSpec = MaterialTheme.motionScheme.defaultSpatialSpec<Float>()

    val enterZoom = scaleIn(
        initialScale = 0.85f,
        animationSpec = bouncyScaleSpec
    ) + fadeIn(animationSpec = bouncyScaleSpec)
    val exitZoom = scaleOut(
        targetScale = 0.85f,
        animationSpec = bouncyScaleSpec
    ) + fadeOut(animationSpec = bouncyScaleSpec)



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "mail",
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                navigationIcon = {
                    if (showDrawer) {
                        IconButton(onClick = onOpenDrawer) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Open Drawer"
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Open Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->

        NavigableListDetailPaneScaffold(
            modifier = modifier.padding(innerPadding),
            navigator = navigator,
            listPane = {
                AnimatedPane(
                    enterTransition = enterZoom,
                    exitTransition = exitZoom
                ) {
                    EmailList(
                        emails = state.emails,
                        onEmailClick = { email ->
                            scope.launch {
                                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, email)
                            }
                        }
                    )
                }
            },
            detailPane = {
                AnimatedPane(
                    enterTransition = enterZoom,
                    exitTransition = exitZoom
                ) {
                    lastSelectedEmail?.let { email ->
                        EmailDetail(email = email)
                    } ?: Text(
                        text = "Select an email",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        )
    }
}