package com.mueez.genzhabittracker.utils

import com.mueez.genzhabittracker.data.model.HabitCompletion
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object StreakCalculator {

    fun calculateCurrentStreak(
        completions: List<HabitCompletion>,
        frequency: String
    ): Int {

        if (completions.isEmpty()) {
            return 0
        }

        val dateFormat =
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).apply {
                isLenient = false
            }

        val completedDates =
            completions
                .mapNotNull { completion ->
                    try {
                        dateFormat.parse(
                            completion.completedDate
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                .distinct()
                .sortedDescending()

        if (completedDates.isEmpty()) {
            return 0
        }

        return when (frequency) {

            "Weekly" -> {
                calculateWeeklyStreak(
                    completedDates
                )
            }

            else -> {
                calculateDailyStreak(
                    completedDates
                )
            }
        }
    }

    private fun calculateDailyStreak(
        completedDates: List<Date>
    ): Int {

        val today =
            Calendar.getInstance()

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
                formatDate(date) ==
                formatDate(expectedDate.time)
            ) {
                streak++
            } else {
                break
            }
        }

        return streak
    }

    private fun calculateWeeklyStreak(
        completedDates: List<Date>
    ): Int {

        val currentWeek =
            Calendar.getInstance()

        var streak = 0

        val completedWeekStarts =
            completedDates
                .map { date ->
                    getWeekStart(date)
                }
                .distinct()
                .sortedDescending()

        for (weekStart in completedWeekStarts) {

            val expectedWeekStart =
                Calendar.getInstance().apply {

                    time = currentWeek.time

                    add(
                        Calendar.WEEK_OF_YEAR,
                        -streak
                    )

                    set(
                        Calendar.DAY_OF_WEEK,
                        firstDayOfWeek
                    )

                    set(
                        Calendar.HOUR_OF_DAY,
                        0
                    )

                    set(
                        Calendar.MINUTE,
                        0
                    )

                    set(
                        Calendar.SECOND,
                        0
                    )

                    set(
                        Calendar.MILLISECOND,
                        0
                    )
                }

            if (
                sameDay(
                    weekStart,
                    expectedWeekStart.time
                )
            ) {
                streak++
            } else {
                break
            }
        }

        return streak
    }

    private fun getWeekStart(
        date: Date
    ): Date {

        val calendar =
            Calendar.getInstance().apply {

                time = date

                set(
                    Calendar.DAY_OF_WEEK,
                    firstDayOfWeek
                )

                set(
                    Calendar.HOUR_OF_DAY,
                    0
                )

                set(
                    Calendar.MINUTE,
                    0
                )

                set(
                    Calendar.SECOND,
                    0
                )

                set(
                    Calendar.MILLISECOND,
                    0
                )
            }

        return calendar.time
    }

    private fun sameDay(
        first: Date,
        second: Date
    ): Boolean {

        return formatDate(first) ==
                formatDate(second)
    }

    private fun formatDate(
        date: Date
    ): String {

        val dateFormat =
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )

        return dateFormat.format(date)
    }
}