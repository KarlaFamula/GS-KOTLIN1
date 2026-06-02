package com.github.karlaeisaque.gs.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val SpaceDarkColorScheme = darkColorScheme(
    primary = SpaceAccent,
    onPrimary = SpaceBlack,
    primaryContainer = SpaceBlue,
    onPrimaryContainer = SpaceAccent,
    secondary = SpaceGreen,
    onSecondary = SpaceBlack,
    background = SpaceBlack,
    onBackground = androidx.compose.ui.graphics.Color.White,
    surface = SpaceSurface,
    onSurface = androidx.compose.ui.graphics.Color.White,
    surfaceVariant = SpaceCard,
    onSurfaceVariant = SpaceGray,
    error = SpaceOrange,
    onError = androidx.compose.ui.graphics.Color.White
)

@Composable
fun SpaceMonitorTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = SpaceDarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = SpaceBlack.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
