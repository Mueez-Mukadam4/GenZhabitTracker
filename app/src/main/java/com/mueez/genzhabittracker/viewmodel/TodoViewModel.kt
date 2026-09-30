
package com.mueez.genzhabittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mueez.genzhabittracker.data.model.Todo
import com.mueez.genzhabittracker.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    fun addTodo(
        todo: Todo,
        onResult: (List<Todo>) -> Unit
    ) {
        viewModelScope.launch {

            repository.insertTodo(todo)

            val todos = repository.getAllTodos()

            onResult(todos)
        }
    }

    fun getAllTodos(
        onResult: (List<Todo>) -> Unit
    ) {
        viewModelScope.launch {

            val todos = repository.getAllTodos()

            onResult(todos)
        }
    }

    fun updateTodo(
        todo: Todo,
        onResult: (List<Todo>) -> Unit
    ) {
        viewModelScope.launch {

            repository.updateTodo(todo)

            val todos = repository.getAllTodos()

            onResult(todos)
        }
    }

    fun deleteTodo(
        todo: Todo,
        onResult: (List<Todo>) -> Unit
    ) {
        viewModelScope.launch {

            repository.deleteTodo(todo)

            val todos = repository.getAllTodos()

            onResult(todos)
        }
    }

    fun deleteCompletedTodos(
        onResult: (List<Todo>) -> Unit
    ) {
        viewModelScope.launch {

            repository.deleteCompletedTodos()

            val todos = repository.getAllTodos()

            onResult(todos)
        }
    }
}

