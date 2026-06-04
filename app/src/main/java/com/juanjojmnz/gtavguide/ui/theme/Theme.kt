package com.juanjojmnz.gtavguide.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val GTAColorScheme = darkColorScheme(
    primary            = GTAGreen,
    onPrimary          = GTABackground,
    primaryContainer   = GTAGreenDark,
    onPrimaryContainer = GTAGreenLight,
    secondary          = GTAYellow,
    onSecondary        = GTABackground,
    tertiary           = GTAOrange,
    onTertiary         = GTABackground,
    error              = GTARed,
    background         = GTABackground,
    onBackground       = GTATextPrimary,
    surface            = GTASurface,
    onSurface          = GTATextPrimary,
    surfaceVariant     = GTASurfaceVariant,
    onSurfaceVariant   = GTATextSecondary,
    outline            = GTABorder,
    outlineVariant     = GTADivider,
    scrim              = GTAOverlayDark
)

@Composable
fun GTAVGuideTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = GTABackground.toArgb()
            window.navigationBarColor = GTABackground.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = GTAColorScheme,
        typography  = GTATypography,
        content     = content
    )
}