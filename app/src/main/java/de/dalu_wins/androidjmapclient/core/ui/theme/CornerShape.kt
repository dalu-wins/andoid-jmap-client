package de.dalu_wins.androidjmapclient.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp


data object CornerShape {
    private val bigCornerRadius = 16.dp
    private val smallCornerRadius = 4.dp

    val Top = RoundedCornerShape(
        topStart = bigCornerRadius,
        topEnd = bigCornerRadius,
        bottomStart = smallCornerRadius,
        bottomEnd = smallCornerRadius
    )

    val Bottom = RoundedCornerShape(
        topStart = smallCornerRadius,
        topEnd = smallCornerRadius,
        bottomStart = bigCornerRadius,
        bottomEnd = bigCornerRadius
    )

    val None = RoundedCornerShape(smallCornerRadius)

    val All = RoundedCornerShape(bigCornerRadius)

    val ReceiveMsg = RoundedCornerShape(
        topStart = smallCornerRadius,
        topEnd = bigCornerRadius,
        bottomStart = bigCornerRadius,
        bottomEnd = bigCornerRadius
    )

    val SendMsg = RoundedCornerShape(
        topStart = bigCornerRadius,
        topEnd = smallCornerRadius,
        bottomStart = bigCornerRadius,
        bottomEnd = bigCornerRadius
    )
}