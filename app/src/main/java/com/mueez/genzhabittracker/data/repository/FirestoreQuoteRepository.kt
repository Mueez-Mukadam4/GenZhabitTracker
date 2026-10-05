package com.mueez.genzhabittracker.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mueez.genzhabittracker.data.model.Quote
import kotlinx.coroutines.tasks.await

class FirestoreQuoteRepository {

    private val firestore =
        FirebaseFirestore.getInstance()

    suspend fun getQuotes(): Result<List<Quote>> {
        return try {

            val snapshot =
                firestore
                    .collection("quotes")
                    .get()
                    .await()

            val quotes =
                snapshot.documents.mapNotNull { document ->
                    document.toObject(Quote::class.java)
                }

            Result.success(quotes)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}