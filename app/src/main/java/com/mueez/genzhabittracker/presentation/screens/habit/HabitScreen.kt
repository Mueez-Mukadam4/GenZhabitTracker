
package com.mueez.genzhabittracker.presentation.screens.habit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mueez.genzhabittracker.data.database.DatabaseProvider
import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.repository.HabitRepository
import com.mueez.genzhabittracker.viewmodel.HabitViewModel
import com.mueez.genzhabittracker.viewmodel.HabitViewModelFactory

@Composable
fun HabitScreen(
    navController: NavController
) {

    val habitDao = DatabaseProvider.database.habitDao()
    val habitRepository = HabitRepository(habitDao)
    val factory = HabitViewModelFactory(habitRepository)

    val habitViewModel: HabitViewModel = viewModel(
        factory = factory
    )

    var habitName by remember {
        mutableStateOf("")
    }

    var frequency by remember {
        mutableStateOf("Daily")
    }

    var frequencyExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Top-left back arrow
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text(
                    text = "←",
                    fontSize = 28.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Create a Habit",
            color = Color.Black,
            fontSize = 28.sp
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
                    text = "Habit name",
                    color = Color.Black
                )
            },
            placeholder = {
                Text(
                    text = "Enter your habit...",
                    color = Color.Gray
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        TextButton(
            onClick = {
                frequencyExpanded = true
            }
        ) {
            Text(
                text = "Frequency: $frequency",
                color = Color.Black,
                fontSize = 16.sp
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
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {
                if (habitName.isNotBlank()) {

                    val habit = Habit(
                        name = habitName,
                        frequency = frequency
                    )

                    habitViewModel.addHabit(habit)

                    navController.popBackStack()
                }
            }
        ) {
            Text(
                text = "Save Habit",
                color = Color.White
            )
        }
    }
}

