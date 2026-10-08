package com.mueez.genzhabittracker.presentation.screens.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mueez.genzhabittracker.data.database.DatabaseProvider
import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.repository.HabitRepository
import com.mueez.genzhabittracker.viewmodel.HabitViewModel
import com.mueez.genzhabittracker.viewmodel.HabitViewModelFactory

@Composable
fun EditHabitScreen(
    navController: NavController,
    habit: Habit
) {

    val habitDao = DatabaseProvider.database.habitDao()

    val habitCompletionDao =
        DatabaseProvider.database.habitCompletionDao()

    val habitRepository = HabitRepository(
        habitDao,
        habitCompletionDao
    )

    val factory = HabitViewModelFactory(
        habitRepository
    )

    val habitViewModel: HabitViewModel = viewModel(
        factory = factory
    )

    var habitName by remember {
        mutableStateOf(habit.name)
    }

    var frequency by remember {
        mutableStateOf(habit.frequency)
    }

    var frequencyExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        TextButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text(
                text = "←",
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Edit Habit",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = habitName,
            onValueChange = {
                habitName = it
            },
            label = {
                Text(
                    text = "Habit name"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        TextButton(
            onClick = {
                frequencyExpanded = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Frequency: $frequency",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select frequency"
            )
        }

        DropdownMenu(
            expanded = frequencyExpanded,
            onDismissRequest = {
                frequencyExpanded = false
            }
        ) {

            DropdownMenuItem(
                text = {
                    Text("Daily")
                },
                onClick = {
                    frequency = "Daily"
                    frequencyExpanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text("Weekly")
                },
                onClick = {
                    frequency = "Weekly"
                    frequencyExpanded = false
                }
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {

                if (habitName.isNotBlank()) {

                    val updatedHabit = habit.copy(
                        name = habitName,
                        frequency = frequency
                    )

                    habitViewModel.updateHabit(updatedHabit)

                    navController.popBackStack()
                }
            }
        ) {
            Text(
                text = "Update Habit"
            )
        }
    }
}