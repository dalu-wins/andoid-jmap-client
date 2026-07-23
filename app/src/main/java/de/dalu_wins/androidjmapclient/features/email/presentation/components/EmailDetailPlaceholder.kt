package de.dalu_wins.androidjmapclient.features.email.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import de.dalu_wins.androidjmapclient.core.ui.theme.CornerShape

@Composable
fun EmailDetailPlaceholder(
    modifier: Modifier = Modifier
) {
    val skeletonColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
    val defaults = EmailDetailDefaults

    Column(modifier = modifier) {
        // Header Skeleton matches EmailDetail Row exactly
        Row(
            horizontalArrangement = Arrangement.spacedBy(defaults.HEADER_SPACING),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(defaults.PADDING)
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp) // Default size of InitialsAvatar
                    .clip(CircleShape)
                    .background(skeletonColor)
            )
            Column(modifier = Modifier.weight(1f)) {
                // Sender name
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp) // Matches the text padding in EmailDetail
                        .width(120.dp)
                        .height(18.dp) // Close to titleMedium height
                        .clip(CornerShape.All)
                        .background(skeletonColor)
                )
                Spacer(modifier = Modifier.height(6.dp)) // Small gap
                // "To: ..."
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(14.dp) // Close to titleSmall height
                        .clip(CornerShape.All)
                        .background(skeletonColor)
                )
            }
            // Timestamp
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(12.dp) // labelMedium height
                    .clip(CornerShape.All)
                    .background(skeletonColor)
            )
        }

        // Content Card Skeleton matches EmailDetail Card exactly
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(defaults.PADDING),
            shape = CornerShape.ReceiveMsg,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(defaults.CONTENT_PADDING),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Subject skeleton
                Box(
                    modifier = Modifier
                        .padding(bottom = defaults.SUBJECT_SPACING - 12.dp) // Compensate for spacedBy
                        .fillMaxWidth(0.7f)
                        .height(28.dp) // Close to headlineSmall height
                        .clip(CornerShape.All)
                        .background(skeletonColor)
                )

                // Body lines skeleton
                repeat(8) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(if (index == 7) 0.5f else 1f)
                            .height(16.dp) // Close to bodyLarge height
                            .clip(CornerShape.All)
                            .background(skeletonColor)
                    )
                }
            }
        }
    }
}
