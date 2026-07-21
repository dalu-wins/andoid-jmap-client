package de.dalu_wins.androidjmapclient.main

import de.dalu_wins.androidjmapclient.features.settings.domain.UserSettings

sealed interface MainUiState {
    object Loading : MainUiState
    data class Success(val settings: UserSettings) : MainUiState
}
