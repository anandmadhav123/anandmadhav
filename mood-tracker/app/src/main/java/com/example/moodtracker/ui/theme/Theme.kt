package com.example.moodtracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
	primary = Color(0xFF6750A4),
	secondary = Color(0xFF625B71),
	background = Color(0xFFFFFBFE),
	surface = Color(0xFFFFFBFE),
	onPrimary = Color.White,
	onBackground = Color(0xFF1C1B1F),
	onSurface = Color(0xFF1C1B1F)
)

private val DarkColors = darkColorScheme(
	primary = Color(0xFFD0BCFF),
	secondary = Color(0xFFCCC2DC),
	background = Color(0xFF1C1B1F),
	surface = Color(0xFF1C1B1F),
	onPrimary = Color(0xFF381E72),
	onBackground = Color(0xFFE6E1E5),
	onSurface = Color(0xFFE6E1E5)
)

@Composable
fun MoodTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
	MaterialTheme(
		colorScheme = if (darkTheme) DarkColors else LightColors,
		content = content
	)
}