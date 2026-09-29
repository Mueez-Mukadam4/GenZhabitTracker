
package com.mueez.genzhabittracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mueez.genzhabittracker.data.model.Habit

@Composable
fun HabitCard(
    habit: Habit,
    onCompletionChanged: (Habit) -> Unit,
    onDelete: (Habit) -> Unit,
    onEdit: (Habit) -> Unit
) {

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFE8E8E8),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = habit.name,
                fontSize = 18.sp,
                color = if (habit.isCompleted) {
                    Color.Gray
                } else {
                    Color.Black
                },
                textDecoration = if (habit.isCompleted) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                }
            )

            Text(
                text = habit.frequency,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }

        Checkbox(
            checked = habit.isCompleted,
            onCheckedChange = { checked ->

                val updatedHabit = habit.copy(
                    isCompleted = checked
                )

                onCompletionChanged(updatedHabit)
            }
        )

        TextButton(
            onClick = {
                onEdit(habit)
            }
        ) {
            Text(
                text = "Edit"
            )
        }

        TextButton(
            onClick = {
                showDeleteDialog = true
            }
        ) {
            Text(
                text = "Delete",
                color = Color.Red
            )
        }
    }

    if (showDeleteDialog) {

        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text(
                    text = "Delete Habit?"
                )
            },

            text = {
                Text(
                    text = "Are you sure you want to delete \"${habit.name}\"?"
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete(habit)
                    }
                ) {
                    Text(
                        text = "Delete",
                        color = Color.Red
                    )
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        )
    }
}

