package de.dalu_wins.androidjmapclient.features.settings.domain

data class UserSettings(
    val theme: AppTheme = AppTheme.SYSTEM,
    val dynamicColors: Boolean = true
)
