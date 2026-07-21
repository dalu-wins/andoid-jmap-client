package de.dalu_wins.androidjmapclient.features.email.presentation

import de.dalu_wins.androidjmapclient.features.email.domain.Email

data class EmailListState(
    val emails: List<Email> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
