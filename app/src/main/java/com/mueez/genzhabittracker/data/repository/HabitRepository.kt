package com.mueez.genzhabittracker.data.repository

import com.mueez.genzhabittracker.data.dao.HabitDao
import com.mueez.genzhabittracker.data.dao.HabitCompletionDao
import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.model.HabitCompletion

class HabitRepository(
    private val habitDao: HabitDao,
    private val habitCompletionDao: HabitCompletionDao
) {

    suspend fun insertHabit(habit: Habit) {
        habitDao.insertHabit(habit)
    }

    suspend fun getAllHabits(): List<Habit> {
        return habitDao.getAllHabits()
    }

    suspend fun getHabitById(habitId: Int): Habit? {
        return habitDao.getHabitById(habitId)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }

    suspend fun saveCompletion(
        completion: HabitCompletion
    ) {
        habitCompletionDao.insertCompletion(
            completion
        )
    }

    suspend fun deleteCompletionForDate(
        habitId: Int,
        date: String
    ) {
        habitCompletionDao.deleteCompletionForDate(
            habitId,
            date
        )
    }

    suspend fun getCompletionsForHabit(
        habitId: Int
    ): List<HabitCompletion> {
        return habitCompletionDao.getCompletionsForHabit(
            habitId
        )
    }

    suspend fun getCompletionCountForDate(
        habitId: Int,
        date: String
    ): Int {
        return habitCompletionDao.getCompletionCountForDate(
            habitId,
            date
        )
    }
}