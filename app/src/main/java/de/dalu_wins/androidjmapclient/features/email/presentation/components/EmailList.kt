package de.dalu_wins.androidjmapclient.features.email.presentation.components

import androidx.compose.runtime.Composable
import de.dalu_wins.androidjmapclient.core.ui.components.CustomLazyColumn
import de.dalu_wins.androidjmapclient.core.ui.components.CustomLazyListSection
import de.dalu_wins.androidjmapclient.features.email.domain.Email

@Composable
fun EmailList(
    emails: List<Email>,
    onEmailClick: (Email) -> Unit
) {
    val sections = listOf(CustomLazyListSection(items = emails))

    CustomLazyColumn(
        sections = sections,
        onItemClick = onEmailClick,
        itemContent = { email ->
            EmailListItem(email)
        }
    )
}
