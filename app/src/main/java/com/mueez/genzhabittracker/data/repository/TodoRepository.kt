package com.mueez.genzhabittracker.data.repository

import com.mueez.genzhabittracker.data.dao.TodoDao
import com.mueez.genzhabittracker.data.model.Todo

class TodoRepository(
    private val todoDao: TodoDao
) {

    suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    suspend fun getAllTodos(): List<Todo> {
        return todoDao.getAllTodos()
    }

    suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
    }

    suspend fun deleteCompletedTodos() {
        todoDao.deleteCompletedTodos()
    }


}