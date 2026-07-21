package de.dalu_wins.androidjmapclient.features.settings.domain

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getUserSettings(): Flow<UserSettings>
    suspend fun setTheme(theme: AppTheme)
    suspend fun setDynamicColors(enabled: Boolean)
}
