package de.dalu_wins.androidjmapclient.features.email.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTopBar(
    mailboxName: String,
    mailboxPath: List<String>,
    showBackButton: Boolean,
    showFilters: Boolean,
    showOnlyUnread: Boolean,
    unreadCount: Int,
    onBackClick: () -> Unit,
    onToggleUnreadFilter: () -> Unit,
    onPathIndexClick: (Int) -> Unit,
    onLastPathItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bouncySpatialSpec = MaterialTheme.motionScheme.defaultSpatialSpec<IntSize>()
    val bouncyAlphaSpec = MaterialTheme.motionScheme.defaultSpatialSpec<Float>()

    Column(modifier = modifier.safeDrawingPadding()) {
        TopAppBar(
            title = { Text(mailboxName) },
            navigationIcon = {
                if (showBackButton) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent
            )
        )
        Row(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = showFilters,
                enter = fadeIn(animationSpec = bouncyAlphaSpec) + expandHorizontally(
                    animationSpec = bouncySpatialSpec
                ),
                exit = fadeOut(animationSpec = bouncyAlphaSpec) + shrinkHorizontally(
                    animationSpec = bouncySpatialSpec
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    FilterChip(
                        selected = showOnlyUnread,
                        onClick = onToggleUnreadFilter,
                        label = { Text("Unread $unreadCount") },
                        leadingIcon = if (showOnlyUnread) {
                            {
                                Icon(
                                    imageVector = Icons.Default.MarkEmailUnread,
                                    contentDescription = null,
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else null
                    )

                    VerticalDivider(
                        modifier = Modifier.height(24.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }

            MailboxPathBreadcrumbs(
                path = mailboxPath,
                onPathIndexClick = onPathIndexClick,
                onLastItemClick = onLastPathItemClick,
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}
