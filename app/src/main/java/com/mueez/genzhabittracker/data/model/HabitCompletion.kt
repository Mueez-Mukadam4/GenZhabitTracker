package com.mueez.genzhabittracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_completions")
data class HabitCompletion(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val habitId: Int,

    val completedDate: String
)