package com.example.moodtracker.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class MoodRepository(private val moodDao: MoodDao) {
	fun moodForDate(date: LocalDate): Flow<MoodEntry?> = moodDao.getByDate(date)

	fun moodsBetween(start: LocalDate, end: LocalDate): Flow<List<MoodEntry>> = moodDao.getBetween(start, end)

	suspend fun setMood(date: LocalDate, mood: Int) {
		moodDao.upsert(MoodEntry(date = date, mood = mood))
	}
}