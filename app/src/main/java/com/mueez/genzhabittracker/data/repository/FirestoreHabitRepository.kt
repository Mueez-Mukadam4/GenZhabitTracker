package com.mueez.genzhabittracker.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.mueez.genzhabittracker.data.model.Habit
import kotlinx.coroutines.tasks.await

class FirestoreHabitRepository {

    private val firestore =
        FirebaseFirestore.getInstance()

    private val firebaseAuth =
        FirebaseAuth.getInstance()

    private fun getUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    suspend fun uploadHabit(
        habit: Habit
    ): Result<Unit> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            firestore
                .collection("users")
                .document(userId)
                .collection("habits")
                .document(habit.id.toString())
                .set(habit, SetOptions.merge())
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun uploadHabits(
        habits: List<Habit>
    ): Result<Unit> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            val batch = firestore.batch()

            habits.forEach { habit ->

                val documentReference =
                    firestore
                        .collection("users")
                        .document(userId)
                        .collection("habits")
                        .document(habit.id.toString())

                batch.set(
                    documentReference,
                    habit,
                    SetOptions.merge()
                )
            }

            batch.commit().await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun deleteHabit(
        habitId: Int
    ): Result<Unit> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            firestore
                .collection("users")
                .document(userId)
                .collection("habits")
                .document(habitId.toString())
                .delete()
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun getHabits(): Result<List<Habit>> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            val snapshot =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection("habits")
                    .get()
                    .await()

            val habits =
                snapshot.documents.mapNotNull { document ->

                    document.toObject(Habit::class.java)
                }

            Result.success(habits)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}