
package com.mueez.genzhabittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.model.HabitCompletion
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

    fun saveCompletion(completion: HabitCompletion) {
        viewModelScope.launch {
            repository.saveCompletion(completion)
        }
    }
    fun saveCompletionIfNeeded(
        completion: HabitCompletion
    ) {
        viewModelScope.launch {

            val count = repository.getCompletionCountForDate(
                completion.habitId,
                completion.completedDate
            )

            if (count == 0) {
                repository.saveCompletion(completion)
            }
        }
    }

    fun getCompletionsForHabit(
        habitId: Int,
        onResult: (List<HabitCompletion>) -> Unit
    ) {
        viewModelScope.launch {
            val completions = repository.getCompletionsForHabit(habitId)
            onResult(completions)
        }
    }
}

