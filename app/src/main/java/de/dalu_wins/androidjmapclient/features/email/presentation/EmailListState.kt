package de.dalu_wins.androidjmapclient.features.email.presentation

import de.dalu_wins.androidjmapclient.features.email.domain.MailboxItem

data class EmailListState(
    val items: List<MailboxItem> = emptyList(),
    val currentMailboxName: String = "Root",
    val mailboxPath: List<String> = listOf("Root"),
    val canNavigateBack: Boolean = false,
    val showOnlyUnread: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
