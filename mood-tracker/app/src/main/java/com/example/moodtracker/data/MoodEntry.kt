package com.example.moodtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "mood_entries")
data class MoodEntry(
	@PrimaryKey val date: LocalDate,
	val mood: Int // 1..5
)