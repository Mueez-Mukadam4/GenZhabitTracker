
package com.mueez.genzhabittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mueez.genzhabittracker.data.repository.QuoteRepository

class QuoteViewModelFactory(
    private val repository: QuoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(QuoteViewModel::class.java)) {
            return QuoteViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

