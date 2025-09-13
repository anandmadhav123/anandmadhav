package com.example.moodtracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MoodDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun upsert(entry: MoodEntry)

	@Query("SELECT * FROM mood_entries WHERE date = :date LIMIT 1")
	fun getByDate(date: LocalDate): Flow<MoodEntry?>

	@Query("SELECT * FROM mood_entries WHERE date BETWEEN :start AND :end ORDER BY date ASC")
	fun getBetween(start: LocalDate, end: LocalDate): Flow<List<MoodEntry>>
}