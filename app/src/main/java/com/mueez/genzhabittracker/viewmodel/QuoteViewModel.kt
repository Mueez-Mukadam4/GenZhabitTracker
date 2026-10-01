
package com.mueez.genzhabittracker.viewmodel

import androidx.lifecycle.ViewModel
import com.mueez.genzhabittracker.data.model.Quote
import com.mueez.genzhabittracker.data.repository.QuoteRepository

class QuoteViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    fun getQuotes(): List<Quote> {
        return repository.getQuotes()
    }
}

