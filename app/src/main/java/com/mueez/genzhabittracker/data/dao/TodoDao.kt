
package com.mueez.genzhabittracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mueez.genzhabittracker.data.model.Todo

@Dao
interface TodoDao {

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Query("SELECT * FROM todos")
    suspend fun getAllTodos(): List<Todo>

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todos WHERE isCompleted = 1")
    suspend fun deleteCompletedTodos()
}

