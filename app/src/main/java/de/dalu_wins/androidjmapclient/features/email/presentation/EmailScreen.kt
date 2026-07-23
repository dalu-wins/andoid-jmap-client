package de.dalu_wins.androidjmapclient.features.email.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
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
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import de.dalu_wins.androidjmapclient.features.email.domain.Email
import de.dalu_wins.androidjmapclient.features.email.domain.Mailbox
import de.dalu_wins.androidjmapclient.features.email.domain.MailboxItem
import de.dalu_wins.androidjmapclient.features.email.presentation.components.EmailDetail
import de.dalu_wins.androidjmapclient.features.email.presentation.components.EmailDetailPlaceholder
import de.dalu_wins.androidjmapclient.features.email.presentation.components.EmailFloatingToolbar
import de.dalu_wins.androidjmapclient.features.email.presentation.components.EmailList
import de.dalu_wins.androidjmapclient.features.email.presentation.components.EmailTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EmailScreen(
    isCompact: Boolean,
    viewModel: EmailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val customDirective = calculatePaneScaffoldDirective(adaptiveInfo).copy(
        horizontalPartitionSpacerSize = 0.dp
    ) // Override to avoid huge gap between panes in tablet mode
    val navigator = rememberListDetailPaneScaffoldNavigator<MailboxItem>(
        scaffoldDirective = customDirective
    )
    val scope = rememberCoroutineScope()
    var lastSelectedEmail by remember { mutableStateOf<Email?>(null) }
    val selectedItem = navigator.currentDestination?.contentKey

    if (selectedItem is Email) {
        lastSelectedEmail = selectedItem
    }

    val canNavigateBackInNavigator = navigator.canNavigateBack()
    val showBackButton = state.canNavigateBack || canNavigateBackInNavigator

    val backAction = {
        if (canNavigateBackInNavigator) {
            scope.launch { navigator.navigateBack() }
        } else {
            viewModel.navigateBack()
        }
    }

    BackHandler(enabled = showBackButton) {
        backAction()
    }

    val bouncyAlphaSpec = MaterialTheme.motionScheme.defaultSpatialSpec<Float>()
    val effectsSpec = MaterialTheme.motionScheme.slowSpatialSpec<Float>()

    val isExpanded =
        navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded

    val showFilters = !isCompact || !isExpanded

    val horizontalBias by animateFloatAsState(
        targetValue = if (isExpanded) 0f else 1f,
        animationSpec = bouncyAlphaSpec,
        label = "toolbarAlignment"
    )

    val unreadCount = state.items.filterIsInstance<Email>().filter { email -> email.isUnread }.size

    Scaffold(
        topBar = {
            EmailTopBar(
                mailboxName = state.currentMailboxName,
                mailboxPath = state.mailboxPath,
                showBackButton = showBackButton,
                showFilters = showFilters,
                showOnlyUnread = state.showOnlyUnread,
                unreadCount = unreadCount,
                onBackClick = { backAction() },
                onToggleUnreadFilter = { viewModel.toggleUnreadFilter() },
                onPathIndexClick = { index ->
                    viewModel.navigateToPathIndex(index)
                    if (canNavigateBackInNavigator) {
                        scope.launch { navigator.navigateBack() }
                    }
                },
                onLastPathItemClick = {
                    if (canNavigateBackInNavigator) {
                        scope.launch { navigator.navigateBack() }
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            EmailFloatingToolbar(
                isExpanded = isExpanded,
                onNewMailClick = { },
                onDeleteClick = { },
                onReplyClick = { },
                modifier = Modifier
                    .align(BiasAlignment(horizontalBias, 1f))
            )
            NavigableListDetailPaneScaffold(
                navigator = navigator,
                listPane = {
                    AnimatedPane(
                        enterTransition = fadeIn(animationSpec = effectsSpec),
                        exitTransition = fadeOut(animationSpec = effectsSpec)
                    ) {
                        AnimatedContent(
                            targetState = state.items,
                            transitionSpec = {
                                fadeIn(animationSpec = effectsSpec) togetherWith
                                        fadeOut(animationSpec = effectsSpec)
                            },
                            label = "listContentTransition"
                        ) { items ->
                            Column {
                                val displayItems = remember(items, state.showOnlyUnread) {
                                    if (state.showOnlyUnread) {
                                        items.filter { item ->
                                            when (item) {
                                                is Email -> item.isUnread
                                                is Mailbox -> false // Don't keep folders visible
                                            }
                                        }
                                    } else {
                                        items
                                    }
                                }

                                EmailList(
                                    items = displayItems,
                                    onItemClick = { item ->
                                        if (item is Email) {
                                            scope.launch {
                                                navigator.navigateTo(
                                                    ListDetailPaneScaffoldRole.Detail,
                                                    item
                                                )
                                            }
                                        } else if (item is Mailbox) {
                                            viewModel.onMailboxClick(item)
                                        }
                                    }
                                )
                            }
                        }
                    }
                },
                detailPane = {
                    AnimatedPane(
                        enterTransition = fadeIn(animationSpec = effectsSpec),
                        exitTransition = fadeOut(animationSpec = effectsSpec)
                    ) {
                        AnimatedContent(
                            targetState = lastSelectedEmail,
                            transitionSpec = {
                                fadeIn(animationSpec = effectsSpec) togetherWith
                                        fadeOut(animationSpec = effectsSpec)
                            },
                            label = "detailContentTransition"
                        ) { email ->
                            email?.let {
                                EmailDetail(email = it)
                            } ?: EmailDetailPlaceholder(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            )
        }
    }
}
