
package com.mueez.genzhabittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.repository.HabitRepository
import kotlinx.coroutines.launch

class HabitViewModel(
    private val repository: HabitRepository
) : ViewModel() {

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repository.insertHabit(habit)
        }
    }

    fun getAllHabits(
        onResult: (List<Habit>) -> Unit
    ) {
        viewModelScope.launch {
            val habits = repository.getAllHabits()
            onResult(habits)
        }
    }
    fun getHabitById(
        habitId: Int,
        onResult: (Habit?) -> Unit
    ) {
        viewModelScope.launch {
            val habit = repository.getHabitById(habitId)
            onResult(habit)
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            repository.updateHabit(habit)
        }
    }
    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }
}

