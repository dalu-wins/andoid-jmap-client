package de.dalu_wins.androidjmapclient.core.ui.components

data class CustomLazyListSection<T>(
    val title: String? = null,
    val items: List<T>
)