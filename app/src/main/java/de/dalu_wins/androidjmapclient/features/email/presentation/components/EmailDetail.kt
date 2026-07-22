package de.dalu_wins.androidjmapclient.features.email.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.dalu_wins.androidjmapclient.core.ui.theme.CornerShape
import de.dalu_wins.androidjmapclient.features.email.domain.Email


object EmailDetailDefaults {
    val HEADER_SPACING = 16.dp
    val PADDING = 12.dp
    val CONTENT_PADDING = 16.dp
    val SUBJECT_SPACING = 16.dp
    val BOTTOM_SPACING = 4.dp
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailDetail(
    email: Email,
    modifier: Modifier = Modifier,
    headerSpacing: Dp = EmailDetailDefaults.HEADER_SPACING,
    padding: Dp = EmailDetailDefaults.PADDING,
    contentPadding: Dp = EmailDetailDefaults.CONTENT_PADDING,
    subjectSpacing: Dp = EmailDetailDefaults.SUBJECT_SPACING,
    bottomSpacing: Dp = EmailDetailDefaults.BOTTOM_SPACING
) {


    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(headerSpacing),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(padding)
        ) {
            InitialsAvatar(name = email.sender)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = email.sender,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "To: ${email.receiver}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = email.timestamp,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(padding)
                .wrapContentHeight(),
            shape = CornerShape.ReceiveMsg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .padding(contentPadding)
            ) {
                Text(
                    text = email.subject,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = subjectSpacing)
                )
                Text(
                    text = email.body,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = bottomSpacing)
                )
            }
        }
    }

}
