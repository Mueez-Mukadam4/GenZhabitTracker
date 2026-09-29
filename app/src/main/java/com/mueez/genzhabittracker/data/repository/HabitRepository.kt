
package com.mueez.genzhabittracker.data.repository

import com.mueez.genzhabittracker.data.dao.HabitDao
import com.mueez.genzhabittracker.data.model.Habit

class HabitRepository(
    private val habitDao: HabitDao
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
}

