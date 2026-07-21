package de.dalu_wins.androidjmapclient.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import de.dalu_wins.androidjmapclient.R
import de.dalu_wins.androidjmapclient.features.email.presentation.EmailScreen
import de.dalu_wins.androidjmapclient.main.structures.NavItem
import kotlinx.coroutines.launch

object EmailNavigationWrapperDefaults {
    val DRAWER_TITLE_HORIZONTAL_PADDING = 8.dp
    val DRAWER_TITLE_VERTICAL_PADDING = 8.dp
    val DRAWER_COLUMN_VERTICAL_PADDING = 8.dp
}

@Composable
fun EmailNavigationWrapper(
    isCompact: Boolean,
    drawerTitleHorizontalPadding: Dp = EmailNavigationWrapperDefaults.DRAWER_TITLE_HORIZONTAL_PADDING,
    drawerTitleVerticalPadding: Dp = EmailNavigationWrapperDefaults.DRAWER_TITLE_VERTICAL_PADDING,
    columnVerticalPadding: Dp = EmailNavigationWrapperDefaults.DRAWER_COLUMN_VERTICAL_PADDING,
    onSettingsClick: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableIntStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isCompact,
        drawerContent = {
            if (isCompact) {
                ModalDrawerSheet(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
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
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    HorizontalDivider()
                    Column(modifier = Modifier.padding(vertical = columnVerticalPadding)) {
                        NavItem.NAV_ITEMS.forEachIndexed { index, item ->
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
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }
                    }
                }
            }
        }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
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
                    NavItem.NAV_ITEMS.forEachIndexed { index, item ->
                        NavigationRailItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }
            }

            EmailScreen(
                showDrawer = isCompact,
                onSettingsClick = onSettingsClick,
                onOpenDrawer = {
                    if (isCompact) scope.launch { drawerState.open() }
                }
            )
        }
    }
}