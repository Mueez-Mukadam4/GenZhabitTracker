
package com.mueez.genzhabittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mueez.genzhabittracker.data.repository.HabitRepository

class HabitViewModelFactory(
    private val repository: HabitRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            return HabitViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

