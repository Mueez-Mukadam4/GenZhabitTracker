
package com.mueez.genzhabittracker.presentation.screens.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.mueez.genzhabittracker.data.database.DatabaseProvider
import com.mueez.genzhabittracker.data.model.Todo
import com.mueez.genzhabittracker.data.repository.TodoRepository
import com.mueez.genzhabittracker.viewmodel.TodoViewModel
import com.mueez.genzhabittracker.viewmodel.TodoViewModelFactory

@Composable
fun TodoScreen(
    navController: NavController
) {

    val todoDao = DatabaseProvider.database.todoDao()

    val todoRepository = TodoRepository(todoDao)

    val factory = TodoViewModelFactory(todoRepository)

    val todoViewModel: TodoViewModel = viewModel(
        factory = factory
    )

    var todos by remember {
        mutableStateOf<List<Todo>>(emptyList())
    }

    var todoTitle by remember {
        mutableStateOf("")
    }

    var duplicateTask by remember {
        mutableStateOf(false)
    }

    var todoToDelete by remember {
        mutableStateOf<Todo?>(null)
    }

    var showClearCompletedDialog by remember {
        mutableStateOf(false)
    }

    var todoToEdit by remember {
        mutableStateOf<Todo?>(null)
    }

    var editedTodoTitle by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        todoViewModel.getAllTodos { result ->
            todos = result
        }
    }

    val cleanedInput = todoTitle.trim()

    val taskAlreadyExists = todos.any {
        it.title.equals(
            cleanedInput,
            ignoreCase = true
        )
    }

    val completedTodos = todos.count {
        it.isCompleted
    }

    val remainingTodos = todos.size - completedTodos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("Back")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "To-Do List",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "${todos.size} tasks • $completedTodos completed • $remainingTodos remaining"
        )

        if (todos.isNotEmpty()) {

            val progress = (completedTodos * 100) / todos.size

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "$progress% completed",
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            LinearProgressIndicator(
                progress = {
                    completedTodos.toFloat() / todos.size.toFloat()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (completedTodos > 0) {

            Button(
                onClick = {
                    showClearCompletedDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear Tasks ($completedTodos)")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        OutlinedTextField(
            value = todoTitle,
            onValueChange = {

                if (it.length <= 100) {

                    todoTitle = it

                    duplicateTask = false
                }
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black
            ),
            label = {
                Text("Enter a task")
            },
            placeholder = {
                Text("e.g. Complete Kotlin practice")
            },
            supportingText = {

                if (duplicateTask) {

                    Text(
                        text = "This task already exists."
                    )

                } else {

                    Text(
                        text = "${todoTitle.length}/100"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = {

                val cleanedTitle = todoTitle.trim()

                val alreadyExists = todos.any {
                    it.title.equals(
                        cleanedTitle,
                        ignoreCase = true
                    )
                }

                if (cleanedTitle.isEmpty()) {

                    return@Button
                }

                if (alreadyExists) {

                    duplicateTask = true

                } else {

                    todoViewModel.addTodo(
                        Todo(
                            title = cleanedTitle
                        )
                    ) { updatedTodos ->

                        todos = updatedTodos
                    }

                    todoTitle = ""
                    duplicateTask = false
                }
            },
            enabled = todoTitle.trim().isNotEmpty() &&
                    !taskAlreadyExists,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task ✨")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (todos.isEmpty()) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "📝",
                    fontSize = 32.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "You're all clear! ✨",
                    fontWeight = FontWeight.Medium
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Add a task and start getting things done."
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {

                items(
                    items = todos,
                    key = { todo -> todo.id }
                ) { todo ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = todo.isCompleted,
                            onCheckedChange = { isChecked ->

                                todoViewModel.updateTodo(
                                    todo.copy(
                                        isCompleted = isChecked
                                    )
                                ) { updatedTodos ->

                                    todos = updatedTodos
                                }
                            }
                        )

                        Spacer(
                            modifier = Modifier.padding(horizontal = 2.dp)
                        )

                        Text(
                            text = todo.title,
                            modifier = Modifier.weight(1f),
                            color = if (todo.isCompleted) {
                                Color.Gray
                            } else {
                                Color.Unspecified
                            },
                            textDecoration = if (todo.isCompleted) {
                                TextDecoration.LineThrough
                            } else {
                                TextDecoration.None
                            }
                        )

                        Spacer(
                            modifier = Modifier.padding(horizontal = 2.dp)
                        )

                        Button(
                            onClick = {
                                todoToEdit = todo
                                editedTodoTitle = todo.title
                            }
                        ) {
                            Text("Edit")
                        }

                        Spacer(
                            modifier = Modifier.padding(horizontal = 2.dp)
                        )

                        Button(
                            onClick = {
                                todoToDelete = todo
                            }
                        ) {
                            Text("Delete Task")
                        }
                    }

                    HorizontalDivider()

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                }
            }
        }

        if (todoToDelete != null) {

            AlertDialog(
                onDismissRequest = {
                    todoToDelete = null
                },
                title = {
                    Text("Delete task?")
                },
                text = {
                    Text(
                        "Are you sure you want to delete \"${todoToDelete?.title}\"?"
                    )
                },
                confirmButton = {

                    TextButton(
                        onClick = {

                            todoToDelete?.let { todo ->

                                todoViewModel.deleteTodo(
                                    todo
                                ) { updatedTodos ->

                                    todos = updatedTodos
                                }
                            }

                            todoToDelete = null
                        }
                    ) {
                        Text("Delete Task")
                    }
                },
                dismissButton = {

                    TextButton(
                        onClick = {
                            todoToDelete = null
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (showClearCompletedDialog) {

            AlertDialog(
                onDismissRequest = {
                    showClearCompletedDialog = false
                },
                title = {
                    Text("Clear completed tasks?")
                },
                text = {
                    Text(
                        "Are you sure you want to remove all completed tasks?"
                    )
                },
                confirmButton = {

                    TextButton(
                        onClick = {

                            todoViewModel.deleteCompletedTodos { updatedTodos ->

                                todos = updatedTodos
                            }

                            showClearCompletedDialog = false
                        }
                    ) {
                        Text("Clear Tasks")
                    }
                },
                dismissButton = {

                    TextButton(
                        onClick = {
                            showClearCompletedDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (todoToEdit != null) {

            AlertDialog(
                onDismissRequest = {
                    todoToEdit = null
                    editedTodoTitle = ""
                },
                title = {
                    Text("Edit task")
                },
                text = {

                    OutlinedTextField(
                        value = editedTodoTitle,
                        onValueChange = {
                            if (it.length <= 100) {
                                editedTodoTitle = it
                            }
                        },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            color = Color.Black
                        ),
                        label = {
                            Text("Task")
                        },
                        placeholder = {
                            Text("e.g. Complete Kotlin practice")
                        },
                        supportingText = {
                            Text("${editedTodoTitle.length}/100")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {

                    TextButton(
                        onClick = {

                            val cleanedTitle = editedTodoTitle.trim()

                            val duplicateEdit = todos.any {
                                it.id != todoToEdit?.id &&
                                        it.title.equals(
                                            cleanedTitle,
                                            ignoreCase = true
                                        )
                            }

                            if (
                                cleanedTitle.isNotEmpty() &&
                                !duplicateEdit
                            ) {

                                todoToEdit?.let { todo ->

                                    todoViewModel.updateTodo(
                                        todo.copy(
                                            title = cleanedTitle
                                        )
                                    ) { updatedTodos ->

                                        todos = updatedTodos
                                    }
                                }

                                todoToEdit = null
                                editedTodoTitle = ""
                            }
                        }
                    ) {
                        Text("Save Changes")
                    }
                },
                dismissButton = {

                    TextButton(
                        onClick = {
                            todoToEdit = null
                            editedTodoTitle = ""
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

