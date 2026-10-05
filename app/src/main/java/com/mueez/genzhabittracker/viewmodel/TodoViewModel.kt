package com.mueez.genzhabittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mueez.genzhabittracker.data.model.Todo
import com.mueez.genzhabittracker.data.repository.FirestoreTodoRepository
import com.mueez.genzhabittracker.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val firestoreTodoRepository =
        FirestoreTodoRepository()

    fun addTodo(
        todo: Todo,
        onResult: (List<Todo>) -> Unit
    ) {
        viewModelScope.launch {

            val generatedId =
                repository.insertTodo(todo)

            val savedTodo =
                todo.copy(
                    id = generatedId.toInt()
                )

            firestoreTodoRepository.uploadTodo(
                savedTodo
            )

            val todos =
                repository.getAllTodos()

            onResult(todos)
        }
    }

    fun getAllTodos(
        onResult: (List<Todo>) -> Unit
    ) {
        viewModelScope.launch {

            val todos =
                repository.getAllTodos()

            onResult(todos)
        }
    }

    fun updateTodo(
        todo: Todo,
        onResult: (List<Todo>) -> Unit
    ) {
        viewModelScope.launch {

            repository.updateTodo(todo)

            firestoreTodoRepository.uploadTodo(todo)

            val todos =
                repository.getAllTodos()

            onResult(todos)
        }
    }

    fun deleteTodo(
        todo: Todo,
        onResult: (List<Todo>) -> Unit
    ) {
        viewModelScope.launch {

            repository.deleteTodo(todo)

            val deleteResult =
                firestoreTodoRepository.deleteTodo(
                    todo.id
                )

            if (deleteResult.isSuccess) {

                println(
                    "FIRESTORE DELETE SUCCESS: Todo ID ${todo.id}"
                )

            } else {

                println(
                    "FIRESTORE DELETE ERROR: ${
                        deleteResult.exceptionOrNull()?.message
                    }"
                )
            }

            val todos =
                repository.getAllTodos()

            onResult(todos)
        }
    }

    fun deleteCompletedTodos(
        onResult: (List<Todo>) -> Unit
    ) {
        viewModelScope.launch {

            val completedTodos =
                repository.getAllTodos()
                    .filter { it.isCompleted }

            repository.deleteCompletedTodos()

            completedTodos.forEach { todo ->

                val deleteResult =
                    firestoreTodoRepository.deleteTodo(
                        todo.id
                    )

                if (deleteResult.isSuccess) {

                    println(
                        "FIRESTORE DELETE SUCCESS: Todo ID ${todo.id}"
                    )

                } else {

                    println(
                        "FIRESTORE DELETE ERROR: ${
                            deleteResult.exceptionOrNull()?.message
                        }"
                    )
                }
            }

            val todos =
                repository.getAllTodos()

            onResult(todos)
        }
    }

    fun syncTodosFromFirestore(
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {

            val result =
                firestoreTodoRepository.getTodos()

            result.onSuccess { cloudTodos ->

                cloudTodos.forEach { cloudTodo ->

                    val localTodo =
                        repository.getTodoById(
                            cloudTodo.id
                        )

                    if (localTodo == null) {

                        repository.insertTodo(
                            cloudTodo
                        )

                    } else {

                        repository.updateTodo(
                            cloudTodo
                        )
                    }
                }
            }

            onComplete()
        }
    }
}