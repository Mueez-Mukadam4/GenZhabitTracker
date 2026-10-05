package com.mueez.genzhabittracker.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.mueez.genzhabittracker.data.model.HabitCompletion
import kotlinx.coroutines.tasks.await

class FirestoreHabitCompletionRepository {

    private val firestore =
        FirebaseFirestore.getInstance()

    private val firebaseAuth =
        FirebaseAuth.getInstance()

    private fun getUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    suspend fun uploadCompletion(
        completion: HabitCompletion
    ): Result<Unit> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            val documentId =
                "${completion.habitId}_${completion.completedDate}"

            firestore
                .collection("users")
                .document(userId)
                .collection("habitCompletions")
                .document(documentId)
                .set(
                    completion,
                    SetOptions.merge()
                )
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun uploadCompletions(
        completions: List<HabitCompletion>
    ): Result<Unit> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            val batch = firestore.batch()

            completions.forEach { completion ->

                val documentId =
                    "${completion.habitId}_${completion.completedDate}"

                val documentReference =
                    firestore
                        .collection("users")
                        .document(userId)
                        .collection("habitCompletions")
                        .document(documentId)

                batch.set(
                    documentReference,
                    completion,
                    SetOptions.merge()
                )
            }

            batch.commit().await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun getCompletions(): Result<List<HabitCompletion>> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            val snapshot =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection("habitCompletions")
                    .get()
                    .await()

            val completions =
                snapshot.documents.mapNotNull { document ->

                    document.toObject(
                        HabitCompletion::class.java
                    )
                }

            Result.success(completions)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun deleteCompletion(
        habitId: Int,
        completedDate: String
    ): Result<Unit> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            val documentId =
                "${habitId}_${completedDate}"

            firestore
                .collection("users")
                .document(userId)
                .collection("habitCompletions")
                .document(documentId)
                .delete()
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}