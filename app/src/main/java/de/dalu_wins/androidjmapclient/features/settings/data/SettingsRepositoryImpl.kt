package de.dalu_wins.androidjmapclient.features.settings.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import de.dalu_wins.androidjmapclient.features.settings.domain.AppTheme
import de.dalu_wins.androidjmapclient.features.settings.domain.SettingsRepository
import de.dalu_wins.androidjmapclient.features.settings.domain.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsRepositoryImpl @Inject constructor(
    private val context: Context
) : SettingsRepository {

    private object Keys {
        val THEME = stringPreferencesKey("theme")
        val DYNAMIC_COLORS = booleanPreferencesKey("dynamic_colors")
    }

    override fun getUserSettings(): Flow<UserSettings> = context.dataStore.data.map { preferences ->
        val themeString = preferences[Keys.THEME] ?: AppTheme.SYSTEM.name
        val dynamicColors = preferences[Keys.DYNAMIC_COLORS] ?: true
        
        UserSettings(
            theme = AppTheme.valueOf(themeString),
            dynamicColors = dynamicColors
        )
    }

    override suspend fun setTheme(theme: AppTheme) {
        context.dataStore.edit { preferences ->
            preferences[Keys.THEME] = theme.name
        }
    }

    override suspend fun setDynamicColors(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[Keys.DYNAMIC_COLORS] = enabled
        }
    }
}
