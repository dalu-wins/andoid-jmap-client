package de.dalu_wins.androidjmapclient.features.email.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.dalu_wins.androidjmapclient.core.ui.components.CustomLazyColumn
import de.dalu_wins.androidjmapclient.core.ui.components.CustomLazyListSection
import de.dalu_wins.androidjmapclient.features.email.domain.Email

@Composable
fun EmailList(
    emails: List<Email>,
    onEmailClick: (Email) -> Unit,
    modifier: Modifier = Modifier
) {
    val sections = listOf(CustomLazyListSection(title = "Inbox", items = emails))

    CustomLazyColumn(
        sections = sections,
        modifier = modifier,
        onItemClick = onEmailClick,
        itemContent = { email ->
            EmailListItem(email)
        }
    )
}
