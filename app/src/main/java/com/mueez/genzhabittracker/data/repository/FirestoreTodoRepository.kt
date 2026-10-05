package com.mueez.genzhabittracker.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.mueez.genzhabittracker.data.model.Todo
import kotlinx.coroutines.tasks.await

class FirestoreTodoRepository {

    private val firestore =
        FirebaseFirestore.getInstance()

    private val firebaseAuth =
        FirebaseAuth.getInstance()

    private fun getUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    suspend fun uploadTodo(
        todo: Todo
    ): Result<Unit> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            firestore
                .collection("users")
                .document(userId)
                .collection("todos")
                .document(todo.id.toString())
                .set(
                    todo,
                    SetOptions.merge()
                )
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun uploadTodos(
        todos: List<Todo>
    ): Result<Unit> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            val batch = firestore.batch()

            todos.forEach { todo ->

                val documentReference =
                    firestore
                        .collection("users")
                        .document(userId)
                        .collection("todos")
                        .document(todo.id.toString())

                batch.set(
                    documentReference,
                    todo,
                    SetOptions.merge()
                )
            }

            batch.commit().await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun getTodos(): Result<List<Todo>> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            val snapshot =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection("todos")
                    .get()
                    .await()

            val todos =
                snapshot.documents.mapNotNull { document ->

                    document.toObject(
                        Todo::class.java
                    )
                }

            Result.success(todos)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun deleteTodo(
        todoId: Int
    ): Result<Unit> {

        return try {

            val userId = getUserId()
                ?: return Result.failure(
                    Exception("User is not logged in.")
                )

            firestore
                .collection("users")
                .document(userId)
                .collection("todos")
                .document(todoId.toString())
                .delete()
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}