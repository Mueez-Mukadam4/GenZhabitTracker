package com.mueez.genzhabittracker.utils

object RecoveryMode {

    fun shouldOfferRecovery(
        currentStreak: Int,
        completedToday: Boolean
    ): Boolean {
        return currentStreak == 0 && !completedToday
    }
}