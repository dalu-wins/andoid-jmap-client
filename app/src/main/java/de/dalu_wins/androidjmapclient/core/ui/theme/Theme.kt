package de.dalu_wins.androidjmapclient.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = Red80,
    onPrimary = Red20,
    primaryContainer = Orange30,
    onPrimaryContainer = Orange90,
    secondary = RedGrey80,
    onSecondary = RedGrey20,
    secondaryContainer = RedGrey30,
    onSecondaryContainer = RedGrey90,
    tertiary = Orange80,
    onTertiary = Orange20,
    tertiaryContainer = Orange30,
    onTertiaryContainer = Orange90
)

val LightColorScheme = lightColorScheme(
    primary = Red40,
    onPrimary = Color.White,
    primaryContainer = Orange90,
    onPrimaryContainer = Orange10,
    secondary = RedGrey40,
    onSecondary = Color.White,
    secondaryContainer = RedGrey90,
    onSecondaryContainer = RedGrey10,
    tertiary = Orange40,
    onTertiary = Color.White,
    tertiaryContainer = Orange90,
    onTertiaryContainer = Orange10
)
