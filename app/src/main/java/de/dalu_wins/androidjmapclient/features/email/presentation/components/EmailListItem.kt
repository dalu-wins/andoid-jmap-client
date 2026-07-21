package de.dalu_wins.androidjmapclient.features.email.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.dalu_wins.androidjmapclient.features.email.domain.Email


object EmailListItemDefaults {
    val ROW_SPACING = 16.dp
}

@Composable
fun EmailListItem(
    email: Email,
    rowSpacing: Dp = EmailListItemDefaults.ROW_SPACING
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(rowSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialsAvatar(name = email.sender)
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = email.sender,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = email.subject,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
        Column {
            Text(
                text = email.timestamp,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}