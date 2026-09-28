
package com.mueez.genzhabittracker.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
fun HomeScreen(
    navController: NavController
) {

    val habitDao = DatabaseProvider.database.habitDao()
    val habitRepository = HabitRepository(habitDao)
    val factory = HabitViewModelFactory(habitRepository)

    val habitViewModel: HabitViewModel = viewModel(
        factory = factory
    )

    var habits by remember {
        mutableStateOf<List<Habit>>(emptyList())
    }

    LaunchedEffect(Unit) {
        habitViewModel.getAllHabits { result ->
            habits = result
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Good morning 👋",
            fontSize = 28.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Ready to build your day?",
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = "Your Progress",
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "0% Complete",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Start your first habit to begin tracking your progress.",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = "Today's Habits",
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (habits.isEmpty()) {

            Text(
                text = "No habits yet",
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Create your first habit and start your journey.",
                fontSize = 14.sp
            )

        } else {

            habits.forEach { habit ->

                Text(
                    text = "${habit.name} • ${habit.frequency}",
                    fontSize = 16.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = {
                navController.navigate("habit")
            }
        ) {
            Text(
                text = "Create Habit"
            )
        }
    }
}

