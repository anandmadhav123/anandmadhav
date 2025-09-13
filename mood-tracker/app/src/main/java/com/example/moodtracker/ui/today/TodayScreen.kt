package com.example.moodtracker.ui.today

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moodtracker.viewmodel.MoodViewModel

private val moodEmojis = listOf("😞", "🙁", "😐", "🙂", "😄")

@Composable
fun TodayScreen(viewModel: MoodViewModel) {
	val initial = viewModel.todayMood.value
	val selectedMood = remember { mutableIntStateOf(initial ?: 3) }

	LaunchedEffect(initial) {
		if (initial != null) selectedMood.intValue = initial
	}

	Surface(modifier = Modifier.fillMaxSize()) {
		Column(
			modifier = Modifier
				.padding(horizontal = 24.dp, vertical = 32.dp)
				.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Top
		) {
			Text(
				text = "How are you feeling today?",
				style = MaterialTheme.typography.headlineSmall,
				fontWeight = FontWeight.Bold
			)
			Spacer(Modifier.height(24.dp))
			Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
				moodEmojis.forEachIndexed { index, emoji ->
					Text(
						text = emoji,
						modifier = Modifier
							.selectable(
								selected = selectedMood.intValue == index + 1,
								role = Role.RadioButton,
								onClick = { selectedMood.intValue = index + 1 }
							)
							.padding(12.dp),
						style = MaterialTheme.typography.displaySmall
					)
				}
			}
			Spacer(Modifier.height(24.dp))
			Button(onClick = { viewModel.setTodayMood(selectedMood.intValue) }) {
				Text("Save")
			}
		}
	}
}