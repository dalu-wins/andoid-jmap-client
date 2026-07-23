package de.dalu_wins.androidjmapclient.features.email.domain

import kotlinx.parcelize.Parcelize

@Parcelize
data class Email(
    override val id: String,
    val sender: String,
    val receiver: String,
    val subject: String,
    val body: String,
    val timestamp: String,
    val isUnread: Boolean = false
) : MailboxItem()
