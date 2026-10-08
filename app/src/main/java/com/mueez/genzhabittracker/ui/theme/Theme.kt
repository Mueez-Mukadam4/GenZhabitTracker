package com.mueez.genzhabittracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorLightBackground = Color(0xFFF8F8F8)
private val ColorLightSurface = Color(0xFFFFFFFF)
private val ColorLightSurfaceVariant = Color(0xFFEDEDED)

private val ColorLightTextPrimary = Color(0xFF151515)
private val ColorLightTextSecondary = Color(0xFF5F6368)

private val DarkColorScheme = darkColorScheme(
    primary = GenZRed,
    onPrimary = GenZTextPrimary,

    secondary = GenZRedLight,
    onSecondary = GenZTextPrimary,

    tertiary = GenZStreak,
    onTertiary = GenZTextPrimary,

    background = GenZBackground,
    onBackground = GenZTextPrimary,

    surface = GenZSurface,
    onSurface = GenZTextPrimary,

    surfaceVariant = GenZSurfaceVariant,
    onSurfaceVariant = GenZTextSecondary,

    error = GenZError,
    onError = GenZTextPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = GenZRedDark,
    onPrimary = GenZTextPrimary,

    secondary = GenZRed,
    onSecondary = GenZTextPrimary,

    tertiary = GenZStreak,
    onTertiary = GenZTextPrimary,

    background = ColorLightBackground,
    onBackground = ColorLightTextPrimary,

    surface = ColorLightSurface,
    onSurface = ColorLightTextPrimary,

    surfaceVariant = ColorLightSurfaceVariant,
    onSurfaceVariant = ColorLightTextSecondary,

    error = GenZError,
    onError = GenZTextPrimary
)

@Composable
fun GenZhabitTrackerTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}