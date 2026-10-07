package com.mueez.genzhabittracker.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mueez.genzhabittracker.data.database.DatabaseProvider
import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.model.HabitCompletion
import com.mueez.genzhabittracker.data.repository.HabitRepository
import com.mueez.genzhabittracker.presentation.components.HabitCard
import com.mueez.genzhabittracker.utils.MomentumCalculator
import com.mueez.genzhabittracker.utils.RecoveryMode
import com.mueez.genzhabittracker.utils.StreakShield
import com.mueez.genzhabittracker.utils.YearPixelCalculator
import com.mueez.genzhabittracker.viewmodel.HabitViewModel
import com.mueez.genzhabittracker.viewmodel.HabitViewModelFactory

@Composable
fun HomeScreen(
    navController: NavController,
    onLogoutClick: () -> Unit
) {
    val habitDao =
        DatabaseProvider.database.habitDao()

    val habitCompletionDao =
        DatabaseProvider.database.habitCompletionDao()

    val habitRepository =
        HabitRepository(
            habitDao,
            habitCompletionDao
        )

    val factory =
        HabitViewModelFactory(
            habitRepository
        )

    val habitViewModel: HabitViewModel =
        viewModel(
            factory = factory
        )

    var habits by remember {
        mutableStateOf<List<Habit>>(emptyList())
    }

    var habitStreaks by remember {
        mutableStateOf<Map<Int, Int>>(emptyMap())
    }

    LaunchedEffect(Unit) {
        habitViewModel.syncHabitsFromFirestore {
            habitViewModel.getAllHabits { result ->
                habits = result

                result.forEach { habit ->
                    habitViewModel.getCurrentStreak(
                        habit.id
                    ) { streak ->

                        habitStreaks =
                            habitStreaks +
                                    (habit.id to streak)
                    }
                }
            }
        }
    }

    val completedHabits =
        habits.count {
            it.isCompleted
        }

    val progress =
        if (habits.isNotEmpty()) {
            (completedHabits * 100) / habits.size
        } else {
            0
        }

    val completedToday =
        habits.any {
            it.isCompleted
        }

    val highestStreak =
        habitStreaks.values.maxOrNull() ?: 0

    val momentum =
        MomentumCalculator.calculateMomentum(
            currentStreak = highestStreak
        )

    val streakShieldAvailable = true

    val canUseStreakShield =
        StreakShield.canUseShield(
            streak = highestStreak,
            shieldAvailable = streakShieldAvailable
        )

    val shouldOfferRecovery =
        RecoveryMode.shouldOfferRecovery(
            currentStreak = highestStreak,
            completedToday = completedToday
        )

    val currentYearDay =
        YearPixelCalculator.getDayOfYear()

    val totalDaysInYear =
        YearPixelCalculator.getTotalDaysInYear()

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

        Text(
            text = "GenZhabit Tracker",
            fontSize = 14.sp
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
            text = "$progress% Complete",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = if (habits.isEmpty()) {
                "Start your first habit to begin tracking your progress."
            } else if (progress == 100) {
                "Amazing! You completed all your habits today. 🎉"
            } else {
                "Keep going! Every small step counts. 💪"
            },
            fontSize = 14.sp
        )

        if (shouldOfferRecovery) {
            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "🌱 Recovery Mode: Start again today. Missing a day doesn't mean you failed.",
                fontSize = 14.sp
            )
        }

        if (canUseStreakShield) {
            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "🛡️ Streak Shield available",
                fontSize = 14.sp
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "🔥 Momentum: $momentum",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "📅 Year in Pixels: Day $currentYearDay / $totalDaysInYear",
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

                HabitCard(
                    habit = habit,
                    streak = habitStreaks[habit.id] ?: 0,

                    onCompletionChanged = { updatedHabit ->

                        habitViewModel.updateHabit(
                            updatedHabit
                        )

                        if (updatedHabit.isCompleted) {

                            val completion =
                                HabitCompletion(
                                    habitId =
                                        updatedHabit.id,

                                    completedDate =
                                        java.text.SimpleDateFormat(
                                            "yyyy-MM-dd",
                                            java.util.Locale.getDefault()
                                        ).format(
                                            java.util.Date()
                                        )
                                )

                            habitViewModel.saveCompletionIfNeeded(
                                completion
                            ) {

                                habitViewModel.getCurrentStreak(
                                    updatedHabit.id
                                ) { streak ->

                                    habitStreaks =
                                        habitStreaks +
                                                (
                                                        updatedHabit.id to streak
                                                        )
                                }
                            }

                        } else {

                            habitViewModel.getCurrentStreak(
                                updatedHabit.id
                            ) { streak ->

                                habitStreaks =
                                    habitStreaks +
                                            (
                                                    updatedHabit.id to streak
                                                    )
                            }
                        }

                        habitViewModel.getAllHabits {
                                updatedHabits ->

                            habits = updatedHabits
                        }
                    },

                    onDelete = { habitToDelete ->

                        habitViewModel.deleteHabit(
                            habitToDelete
                        )

                        habitViewModel.getAllHabits {
                                updatedHabits ->

                            habits = updatedHabits

                            habitStreaks =
                                habitStreaks -
                                        habitToDelete.id
                        }
                    },

                    onEdit = { habitToEdit ->

                        navController.navigate(
                            "edit/${habitToEdit.id}"
                        )
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = {
                navController.navigate("habit")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "+ Create Habit"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Plan & Reflect",
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Review your habit progress and stay consistent.",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = {
                navController.navigate("calendar")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "📅 Calendar"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = {
                navController.navigate("todo")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "📝 To-Do List"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = {
                navController.navigate("quotes")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "✨ Motivational Quotes"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = {
                navController.navigate("yearinpixels")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "📊 Year in Pixels"
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Logout"
            )
        }
    }
}