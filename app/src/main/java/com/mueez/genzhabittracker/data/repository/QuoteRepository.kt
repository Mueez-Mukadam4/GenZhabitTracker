
package com.mueez.genzhabittracker.data.repository

import com.mueez.genzhabittracker.data.model.Quote
import com.mueez.genzhabittracker.data.source.QuoteData

class QuoteRepository {

    fun getQuotes(): List<Quote> {
        return QuoteData.quotes
    }
}

