package com.mueez.genzhabittracker.utils

object YearPixelCalculator {

    fun getDayOfYear(): Int {
        return java.util.Calendar.getInstance()
            .get(java.util.Calendar.DAY_OF_YEAR)
    }

    fun getTotalDaysInYear(): Int {
        val calendar = java.util.Calendar.getInstance()

        return calendar.getActualMaximum(
            java.util.Calendar.DAY_OF_YEAR
        )
    }

    fun getCompletedDayCount(
        completedDates: List<String>
    ): Int {
        return completedDates
            .distinct()
            .size
    }
}