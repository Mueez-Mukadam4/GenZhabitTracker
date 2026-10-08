package com.mueez.genzhabittracker.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.mueez.genzhabittracker.data.repository.HabitRepository
import com.mueez.genzhabittracker.utils.MomentumCalculator
import com.mueez.genzhabittracker.utils.RecoveryMode
import com.mueez.genzhabittracker.utils.StreakShield
import com.mueez.genzhabittracker.utils.YearPixelCalculator
import com.mueez.genzhabittracker.viewmodel.HabitViewModel
import com.mueez.genzhabittracker.viewmodel.HabitViewModelFactory
import java.util.Calendar

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
            habitViewModel.syncCompletionsFromFirestore {
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

    val currentHour =
        Calendar.getInstance()
            .get(Calendar.HOUR_OF_DAY)

    val greeting =
        when (currentHour) {
            in 5..11 -> "Good morning 👋"
            in 12..16 -> "Good afternoon ☀️"
            in 17..20 -> "Good evening 🌆"
            else -> "Good night 🌙"
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 20.dp,
                vertical = 24.dp
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = greeting,
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "GenZhabit Tracker",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Ready to build your day?",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surfaceVariant
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Your Progress",
                    fontSize = 20.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "$progress%",
                    fontSize = 36.sp,
                    color =
                        MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Complete",
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                LinearProgressIndicator(
                    progress = {
                        progress / 100f
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = when {
                        habits.isEmpty() ->
                            "Start your first habit to begin tracking your progress."

                        progress == 100 ->
                            "Amazing! You completed all your habits today. 🎉"

                        else ->
                            "Keep going! Every small step counts. 💪"
                    },
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (shouldOfferRecovery) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Text(
                    text = "🌱 Recovery Mode\nStart again today. Missing a day doesn't mean you failed.",
                    modifier = Modifier.padding(18.dp),
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.onSurface
                )
            }
        }

        if (canUseStreakShield) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Text(
                    text = "🛡️ Streak Shield available",
                    modifier = Modifier.padding(18.dp),
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "🔥 Momentum",
                        fontSize = 14.sp,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = momentum.toString(),
                        fontSize = 22.sp,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "📅 Year in Pixels",
                        fontSize = 14.sp,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text =
                            "Day $currentYearDay / $totalDaysInYear",
                        fontSize = 16.sp,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}