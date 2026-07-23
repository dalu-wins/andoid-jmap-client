package de.dalu_wins.androidjmapclient.features.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import de.dalu_wins.androidjmapclient.core.ui.components.CustomLazyColumn
import de.dalu_wins.androidjmapclient.core.ui.components.CustomLazyListSection
import de.dalu_wins.androidjmapclient.core.ui.components.SettingsItem
import de.dalu_wins.androidjmapclient.features.settings.domain.AppTheme
import de.dalu_wins.androidjmapclient.features.settings.presentation.structures.RadioOption
import de.dalu_wins.androidjmapclient.features.settings.presentation.structures.SettingType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()

    val themeSettingItems = remember(settings) {
        val themeGroup = SettingType.RadioGroup(
            id = "dark_mode",
            title = "Mode Override",
            options = AppTheme.entries.map { theme ->
                RadioOption(
                    value = theme,
                    label = when (theme) {
                        AppTheme.LIGHT -> "Light"
                        AppTheme.DARK -> "Dark"
                        AppTheme.SYSTEM -> "System Default"
                    }
                )
            },
            selectedValue = settings.theme,
            onOptionSelected = viewModel::onThemeSelected
        )

        val dynamicColorsToggle = SettingType.Toggle(
            id = "dynamic_colors",
            title = "Dynamic Colors",
            subtitle = "Use system colors (Android 12+)",
            isChecked = settings.dynamicColors,
            onToggle = viewModel::onDynamicColorsToggled
        )

        listOf(themeGroup, dynamicColorsToggle)
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.safeDrawingPadding()) {
                TopAppBar(
                    title = { Text("Settings") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    )
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->

        val sections = listOf(
            CustomLazyListSection(title = "Theme", items = themeSettingItems),
        )
        CustomLazyColumn(
            sections = sections,
            modifier = Modifier.padding(innerPadding),
            onItemClick = { item ->
                when (item) {
                    is SettingType.RadioGroupItem -> Unit
                    is SettingType.Toggle -> item.onToggle(!item.isChecked)
                    is SettingType.Action -> item.onClick()
                }
            },
            key = { it.id },
            itemContent = { item ->
                SettingsItem(item = item)
            }
        )
    }
}
