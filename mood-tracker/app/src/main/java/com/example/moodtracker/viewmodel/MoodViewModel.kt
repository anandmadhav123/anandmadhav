package com.example.moodtracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodtracker.data.MoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

enum class TrendsRange { WEEK, MONTH }

class MoodViewModel(private val repository: MoodRepository) : ViewModel() {
	private val today: LocalDate = LocalDate.now()

	val todayMood: StateFlow<Int?> = repository.moodForDate(today)
		.map { it?.mood }
		.stateIn(
			viewModelScope,
			SharingStarted.WhileSubscribed(5_000),
			null
		)

	val weekMoods: StateFlow<List<Pair<LocalDate, Int?>>> = repository
		.moodsBetween(today.minusDays(6), today)
		.map { list ->
			val byDate = list.associateBy { it.date }
			(0L..6L).map { offset ->
				val date = today.minusDays(6 - offset)
				date to byDate[date]?.mood
			}
		}
		.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

	val monthMoods: StateFlow<List<Pair<LocalDate, Int?>>> = repository
		.moodsBetween(today.minusDays(29), today)
		.map { list ->
			val byDate = list.associateBy { it.date }
			(0L..29L).map { offset ->
				val date = today.minusDays(29 - offset)
				date to byDate[date]?.mood
			}
		}
		.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

	fun setTodayMood(mood: Int) {
		viewModelScope.launch {
			repository.setMood(today, mood)
		}
	}
}