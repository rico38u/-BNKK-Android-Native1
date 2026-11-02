package com.bnkk.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BNKKColors = darkColorScheme(
    primary = Color(0xFF9B8CFF),
    onPrimary = Color(0xFF100D24),
    secondary = Color(0xFF50D5E8),
    onSecondary = Color(0xFF062027),
    background = Color(0xFF090B12),
    onBackground = Color(0xFFF3F4FA),
    surface = Color(0xFF111622),
    onSurface = Color(0xFFF3F4FA),
    surfaceVariant = Color(0xFF1C2432),
    onSurfaceVariant = Color(0xFFB9C1D0),
    outline = Color(0xFF394356),
    error = Color(0xFFFF718B)
)

@Composable
fun BNKKTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BNKKColors,
        content = content
    )
}
