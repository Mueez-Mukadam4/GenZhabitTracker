package com.mueez.genzhabittracker.presentation.screens.yearinpixels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mueez.genzhabittracker.data.database.DatabaseProvider
import com.mueez.genzhabittracker.data.model.HabitCompletion
import com.mueez.genzhabittracker.data.repository.HabitRepository
import com.mueez.genzhabittracker.utils.YearPixelCalculator
import com.mueez.genzhabittracker.viewmodel.HabitViewModel
import com.mueez.genzhabittracker.viewmodel.HabitViewModelFactory

@Composable
fun YearInPixelsScreen() {

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

    var completions by remember {
        mutableStateOf<List<HabitCompletion>>(emptyList())
    }

    LaunchedEffect(Unit) {

        habitViewModel.getAllHabits { habits ->

            habits.forEach { habit ->

                habitViewModel.getCompletionsForHabit(
                    habit.id
                ) { result ->

                    completions =
                        completions + result
                }
            }
        }
    }

    val totalDays =
        YearPixelCalculator.getTotalDaysInYear()

    val currentDay =
        YearPixelCalculator.getDayOfYear()

    val completedDates =
        completions
            .map {
                it.completedDate
            }
            .distinct()

    val completedDayCount =
        YearPixelCalculator.getCompletedDayCount(
            completedDates
        )

    val completionPercentage =
        YearPixelCalculator.getCompletionPercentage(
            completedDayCount,
            totalDays
        )

    val days =
        (1..totalDays).toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Year in Pixels",
            fontSize = 24.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Every day is a small pixel in your year.",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Completed days: $completedDayCount",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Year completion: $completionPercentage%",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Today: Day $currentDay / $totalDays",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            items(days) { day ->

                val dateForDay =
                    YearPixelCalculator.getDateForDayOfYear(
                        day
                    )

                val isCompleted =
                    completedDates.contains(
                        dateForDay
                    )

                val pixelColor =
                    when {
                        isCompleted ->
                            Color(0xFF4CAF50)

                        day == currentDay ->
                            Color(0xFF2196F3)

                        day < currentDay ->
                            Color(0xFFBDBDBD)

                        else ->
                            Color(0xFFE8E8E8)
                    }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = pixelColor,
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = day.toString(),
                        fontSize = 9.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}