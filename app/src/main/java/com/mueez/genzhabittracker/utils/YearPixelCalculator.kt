package com.mueez.genzhabittracker.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object YearPixelCalculator {

    fun getDayOfYear(): Int {
        return Calendar.getInstance()
            .get(Calendar.DAY_OF_YEAR)
    }

    fun getTotalDaysInYear(): Int {
        val calendar = Calendar.getInstance()

        return calendar.getActualMaximum(
            Calendar.DAY_OF_YEAR
        )
    }

    fun getCompletedDayCount(
        completedDates: List<String>
    ): Int {
        return completedDates
            .distinct()
            .size
    }

    fun getCompletionPercentage(
        completedDayCount: Int,
        totalDays: Int
    ): Int {
        if (totalDays == 0) {
            return 0
        }

        return (completedDayCount * 100) / totalDays
    }

    fun getDateForDayOfYear(
        dayOfYear: Int
    ): String {

        val calendar =
            Calendar.getInstance().apply {
                set(
                    Calendar.DAY_OF_YEAR,
                    dayOfYear
                )
            }

        val dateFormat =
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )

        return dateFormat.format(
            calendar.time
        )
    }
}