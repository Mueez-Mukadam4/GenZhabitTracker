package com.mueez.genzhabittracker.utils

object StreakShield {

    fun canUseShield(
        streak: Int,
        shieldAvailable: Boolean
    ): Boolean {
        return streak > 0 && shieldAvailable
    }
}