package com.mueez.genzhabittracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mueez.genzhabittracker.data.model.Habit

@Dao
interface HabitDao {
    @Insert
    suspend fun insertHabit(habit: Habit)

    @Query("SELECT * FROM habits")
    suspend fun getAllHabits(): List<Habit>

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)
}