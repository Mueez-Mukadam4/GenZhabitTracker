package com.mueez.genzhabittracker.utils

import com.mueez.genzhabittracker.data.model.HabitCompletion
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object StreakCalculator {

    fun calculateCurrentStreak(
        completions: List<HabitCompletion>
    ): Int {

        if (completions.isEmpty()) {
            return 0
        }

        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val completedDates =
            completions
                .mapNotNull {
                    try {
                        dateFormat.parse(it.completedDate)
                    } catch (e: Exception) {
                        null
                    }
                }
                .distinct()
                .sortedDescending()

        if (completedDates.isEmpty()) {
            return 0
        }

        val today = Calendar.getInstance()

        var streak = 0

        for (date in completedDates) {

            val expectedDate =
                Calendar.getInstance().apply {
                    time = today.time
                    add(
                        Calendar.DAY_OF_YEAR,
                        -streak
                    )
                }

            if (
                dateFormat.format(date) ==
                dateFormat.format(expectedDate.time)
            ) {
                streak++
            } else {
                break
            }
        }

        return streak
    }
}