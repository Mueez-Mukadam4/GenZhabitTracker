package com.mueez.genzhabittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.model.HabitCompletion
import com.mueez.genzhabittracker.data.repository.FirestoreHabitCompletionRepository
import com.mueez.genzhabittracker.data.repository.FirestoreHabitRepository
import com.mueez.genzhabittracker.data.repository.HabitRepository
import kotlinx.coroutines.launch

class HabitViewModel(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val firestoreHabitRepository =
        FirestoreHabitRepository()

    private val firestoreHabitCompletionRepository =
        FirestoreHabitCompletionRepository()

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            habitRepository.insertHabit(habit)
            firestoreHabitRepository.uploadHabit(habit)
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            habitRepository.updateHabit(habit)
            firestoreHabitRepository.uploadHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            habitRepository.deleteHabit(habit)
            firestoreHabitRepository.deleteHabit(habit.id)
        }
    }

    fun getAllHabits(
        onResult: (List<Habit>) -> Unit
    ) {
        viewModelScope.launch {
            val habits = habitRepository.getAllHabits()
            onResult(habits)
        }
    }

    fun getHabitById(
        habitId: Int,
        onResult: (Habit?) -> Unit
    ) {
        viewModelScope.launch {
            val habit =
                habitRepository.getHabitById(habitId)

            onResult(habit)
        }
    }

    fun saveCompletionIfNeeded(
        completion: HabitCompletion
    ) {
        viewModelScope.launch {
            habitRepository.saveCompletion(completion)

            firestoreHabitCompletionRepository
                .uploadCompletion(completion)
        }
    }

    fun getCompletionsForHabit(
        habitId: Int,
        onResult: (List<HabitCompletion>) -> Unit
    ) {
        viewModelScope.launch {
            val completions =
                habitRepository.getCompletionsForHabit(habitId)

            onResult(completions)
        }
    }

    fun syncHabitsFromFirestore(
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {

            val result =
                firestoreHabitRepository.getHabits()

            result.onSuccess { cloudHabits ->

                val localHabits =
                    habitRepository.getAllHabits()

                val cloudHabitIds =
                    cloudHabits.map { it.id }.toSet()

                // Add or update cloud habits locally.
                cloudHabits.forEach { cloudHabit ->

                    val localHabit =
                        habitRepository.getHabitById(
                            cloudHabit.id
                        )

                    if (localHabit == null) {
                        habitRepository.insertHabit(
                            cloudHabit
                        )
                    } else {
                        habitRepository.updateHabit(
                            cloudHabit
                        )
                    }
                }

                // Delete local habits that no longer
                // exist in Firestore.
                localHabits.forEach { localHabit ->

                    if (localHabit.id !in cloudHabitIds) {
                        habitRepository.deleteHabit(
                            localHabit
                        )
                    }
                }
            }

            onComplete()
        }
    }
}