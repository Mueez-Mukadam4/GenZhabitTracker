package com.mueez.genzhabittracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mueez.genzhabittracker.data.model.HabitCompletion

import androidx.room.Query

@Dao
interface HabitCompletionDao {

    @Insert
    suspend fun insertCompletion(
        completion: HabitCompletion
    )


    @Query(
        "SELECT * FROM habit_completions WHERE habitId = :habitId ORDER BY completedDate DESC"
    )
    suspend fun getCompletionsForHabit(
        habitId: Int
    ): List<HabitCompletion>
    @Query(
        "SELECT COUNT(*) FROM habit_completions WHERE habitId = :habitId AND completedDate = :date"
    )
    suspend fun getCompletionCountForDate(
        habitId: Int,
        date: String
    ): Int
}