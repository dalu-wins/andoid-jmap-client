package de.dalu_wins.androidjmapclient.features.email.domain.repository

import de.dalu_wins.androidjmapclient.features.email.domain.MailboxItem
import kotlinx.coroutines.flow.Flow

interface EmailRepository {
    fun getMailboxItems(): Flow<List<MailboxItem>>
}
