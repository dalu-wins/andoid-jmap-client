package de.dalu_wins.androidjmapclient.features.email.domain

import kotlinx.parcelize.Parcelize

@Parcelize
data class Mailbox(
    override val id: String,
    val name: String,
    val items: List<MailboxItem> = emptyList()
) : MailboxItem()
