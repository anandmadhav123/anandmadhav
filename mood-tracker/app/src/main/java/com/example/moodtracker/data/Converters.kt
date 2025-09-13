package com.example.moodtracker.data

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
	@TypeConverter
	fun fromEpochDay(value: Long?): LocalDate? = value?.let { LocalDate.ofEpochDay(it) }

	@TypeConverter
	fun localDateToEpochDay(date: LocalDate?): Long? = date?.toEpochDay()
}