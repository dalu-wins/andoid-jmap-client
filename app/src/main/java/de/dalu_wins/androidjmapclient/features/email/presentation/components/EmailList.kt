package de.dalu_wins.androidjmapclient.features.email.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.dalu_wins.androidjmapclient.core.ui.components.CustomLazyColumn
import de.dalu_wins.androidjmapclient.core.ui.components.CustomLazyListSection
import de.dalu_wins.androidjmapclient.features.email.domain.Email
import de.dalu_wins.androidjmapclient.features.email.domain.Mailbox
import de.dalu_wins.androidjmapclient.features.email.domain.MailboxItem

@Composable
fun EmailList(
    items: List<MailboxItem>,
    onItemClick: (MailboxItem) -> Unit
) {
    if (items.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.Inbox,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Nothing here yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        return
    }

    val mailboxes = items.filterIsInstance<Mailbox>()
    val emails = items.filterIsInstance<Email>()

    val sections = mutableListOf<CustomLazyListSection<MailboxItem>>()

    if (mailboxes.isNotEmpty()) {
        sections.add(CustomLazyListSection(title = "Folders", items = mailboxes))
    }

    if (emails.isNotEmpty()) {
        sections.add(CustomLazyListSection(title = "Emails", items = emails))
    }

    CustomLazyColumn(
        sections = sections,
        onItemClick = onItemClick,
        key = { it.id },
        itemContent = { item ->
            when (item) {
                is Email -> EmailListItem(item)
                is Mailbox -> MailboxListItem(item)
            }
        }
    )
}
