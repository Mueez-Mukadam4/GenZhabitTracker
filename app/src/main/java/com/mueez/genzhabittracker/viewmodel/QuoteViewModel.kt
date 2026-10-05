package com.mueez.genzhabittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mueez.genzhabittracker.data.model.Quote
import com.mueez.genzhabittracker.data.repository.FirestoreQuoteRepository
import com.mueez.genzhabittracker.data.repository.QuoteRepository
import kotlinx.coroutines.launch

class QuoteViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    private val firestoreQuoteRepository =
        FirestoreQuoteRepository()

    fun getQuotes(): List<Quote> {
        return repository.getQuotes()
    }

    fun syncQuotesFromFirestore(
        onResult: (List<Quote>) -> Unit
    ) {
        viewModelScope.launch {

            val result =
                firestoreQuoteRepository.getQuotes()

            result.onSuccess { cloudQuotes ->

                if (cloudQuotes.isNotEmpty()) {
                    onResult(cloudQuotes)
                } else {
                    onResult(repository.getQuotes())
                }

            }.onFailure {

                onResult(repository.getQuotes())
            }
        }
    }
}