package com.example.moodtracker.ui.trends

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moodtracker.viewmodel.MoodViewModel
import java.time.format.DateTimeFormatter

@Composable
fun TrendsScreen(viewModel: MoodViewModel) {
	val range = remember { mutableStateOf(true) } // true=week, false=month
	val data = if (range.value) viewModel.weekMoods.value else viewModel.monthMoods.value

	Surface(modifier = Modifier.fillMaxSize()) {
		Column(
			modifier = Modifier.padding(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Top
		) {
			Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
				FilterChip(
					selected = range.value,
					onClick = { range.value = true },
					label = { Text("This week") }
				)
				FilterChip(
					selected = !range.value,
					onClick = { range.value = false },
					label = { Text("This month") }
				)
			}
			Column(modifier = Modifier
				.fillMaxWidth()
				.padding(top = 24.dp)) {
				Text("Mood trend", style = MaterialTheme.typography.titleMedium)
				MoodBarChart(
					values = data.map { it.second?.toFloat() ?: 0f },
					labels = data.map { it.first.format(DateTimeFormatter.ofPattern(if (range.value) "E" else "d")) },
					height = 200.dp
				)
			}
		}
	}
}

@Composable
private fun MoodBarChart(values: List<Float>, labels: List<String>, height: Dp) {
	Canvas(modifier = Modifier
		.fillMaxWidth()
		.height(height)) {
		drawBarChart(values = values, labels = labels)
	}
}

private fun DrawScope.drawBarChart(values: List<Float>, labels: List<String>) {
	val maxVal = 5f
	val barCount = values.size.coerceAtLeast(1)
	val barWidth = size.width / (barCount * 1.8f)
	val gap = barWidth * 0.8f
	values.forEachIndexed { index, value ->
		val heightRatio = (value / maxVal).coerceIn(0f, 1f)
		val barHeight = size.height * heightRatio
		val left = index * (barWidth + gap)
		drawRoundRect(
			color = Color(0xFF6750A4),
			topLeft = Offset(left, size.height - barHeight),
			size = Size(barWidth, barHeight),
			cornerRadius = CornerRadius(12f, 12f)
		)
	}
}