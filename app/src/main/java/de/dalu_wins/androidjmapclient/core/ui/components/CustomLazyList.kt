package de.dalu_wins.androidjmapclient.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.dalu_wins.androidjmapclient.core.ui.theme.CornerShape

object CustomLazyListDefaults {
    val ITEM_SPACING = 4.dp
    val CONTENT_PADDING = PaddingValues(12.dp)
    val SECTION_SPACING = 24.dp
    val TITLE_PADDING = PaddingValues(bottom = 8.dp, start = 8.dp)
}

@Composable
fun <T> CustomLazyColumn(
    modifier: Modifier = Modifier,
    sections: List<CustomLazyListSection<T>>,
    sectionSpacing: Dp = CustomLazyListDefaults.SECTION_SPACING,
    titlePadding: PaddingValues = CustomLazyListDefaults.TITLE_PADDING,
    onItemClick: ((T) -> Unit)? = null,
    itemModifier: ((T) -> Modifier)? = null,
    contentPadding: PaddingValues = CustomLazyListDefaults.CONTENT_PADDING,
    itemSpacing: Dp = CustomLazyListDefaults.ITEM_SPACING,
    headerContent: @Composable ((String) -> Unit)? = { title ->
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(titlePadding)
        )
    },
    itemContent: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(itemSpacing)
    ) {
        sections.forEachIndexed { sectionIndex, section ->
            section.title?.let { title ->
                item(key = "header_${sectionIndex}_$title") {
                    if (sectionIndex > 0) {
                        Box(Modifier.height(sectionSpacing))
                    }
                    headerContent?.invoke(title)
                }
            }

            itemsIndexed(
                items = section.items
            ) { index, item ->
                val itemShape = when {
                    section.items.size == 1 -> CornerShape.All
                    index == 0 -> CornerShape.Top
                    index == section.items.lastIndex -> CornerShape.Bottom
                    else -> CornerShape.None
                }

                val clickModifier = when {
                    onItemClick != null -> Modifier.clickable { onItemClick(item) }
                    itemModifier != null -> itemModifier(item)
                    else -> Modifier
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(itemShape)
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .then(clickModifier)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    itemContent(item)
                }
            }

        }
    }
}