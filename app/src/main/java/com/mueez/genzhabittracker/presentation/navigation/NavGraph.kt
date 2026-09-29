
package com.mueez.genzhabittracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.mueez.genzhabittracker.data.database.DatabaseProvider
import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.repository.HabitRepository
import com.mueez.genzhabittracker.presentation.screens.calendar.CalendarScreen
import com.mueez.genzhabittracker.presentation.screens.edit.EditHabitScreen
import com.mueez.genzhabittracker.presentation.screens.habit.HabitScreen
import com.mueez.genzhabittracker.presentation.screens.home.HomeScreen
import com.mueez.genzhabittracker.presentation.screens.onboarding.OnboardingScreen
import com.mueez.genzhabittracker.presentation.screens.splash.SplashScreen
import com.mueez.genzhabittracker.viewmodel.HabitViewModel
import com.mueez.genzhabittracker.viewmodel.HabitViewModelFactory

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("onboarding") {
            OnboardingScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("habit") {
            HabitScreen(navController)
        }

        composable("calendar") {

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

            CalendarScreen(
                navController = navController,
                habits = habits
            )
        }

        composable("edit/{habitId}") { backStackEntry ->

            val habitId = backStackEntry
                .arguments
                ?.getString("habitId")
                ?.toIntOrNull()

            var selectedHabit by remember {
                mutableStateOf<Habit?>(null)
            }

            if (habitId != null) {

                val habitDao = DatabaseProvider.database.habitDao()
                val habitRepository = HabitRepository(habitDao)
                val factory = HabitViewModelFactory(habitRepository)

                val habitViewModel: HabitViewModel = viewModel(
                    factory = factory
                )

                LaunchedEffect(habitId) {

                    habitViewModel.getHabitById(habitId) { habit ->

                        selectedHabit = habit
                    }
                }

                selectedHabit?.let { habit ->

                    EditHabitScreen(
                        navController = navController,
                        habit = habit
                    )
                }
            }
        }
    }
}

