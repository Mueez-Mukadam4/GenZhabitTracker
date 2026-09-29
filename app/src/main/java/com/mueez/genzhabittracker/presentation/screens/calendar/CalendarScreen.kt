package com.mueez.genzhabittracker.presentation.screens.calendar

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

import androidx.compose.material3.Text
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextButton

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.model.HabitCompletion

@Composable
fun CalendarScreen(
    navController: NavController,
    habits: List<Habit>,
    completions: List<HabitCompletion>
) {

    val today = SimpleDateFormat(
        "EEEE, dd MMMM yyyy",
        Locale.getDefault()
    ).format(Date())

    val dayNumber = SimpleDateFormat(
        "dd",
        Locale.getDefault()
    ).format(Date())

    val currentMonth = SimpleDateFormat(
        "MMMM yyyy",
        Locale.getDefault()
    ).format(Date())

    val calendar = java.util.Calendar.getInstance()

    val year = calendar.get(
        java.util.Calendar.YEAR
    )

    val month = calendar.get(
        java.util.Calendar.MONTH
    )

    calendar.set(
        java.util.Calendar.DAY_OF_MONTH,
        1
    )

    val firstDayOfMonth = calendar.get(
        java.util.Calendar.DAY_OF_WEEK
    )

    val daysInMonth = when (month) {

        java.util.Calendar.FEBRUARY -> {

            if (
                year % 400 == 0 ||
                (year % 4 == 0 && year % 100 != 0)
            ) {
                29
            } else {
                28
            }
        }

        java.util.Calendar.APRIL,
        java.util.Calendar.JUNE,
        java.util.Calendar.SEPTEMBER,
        java.util.Calendar.NOVEMBER -> {
            30
        }

        else -> {
            31
        }
    }

    val completedHabits = habits.count {
        it.isCompleted
    }

    val completionPercentage = if (habits.isNotEmpty()) {
        (completedHabits * 100) / habits.size
    } else {
        0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        TextButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text(
                text = "←",
                fontSize = 28.sp
            )
        }

        Text(
            text = "Calendar",
            fontSize = 28.sp
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = today,
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Today's Summary",
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Completed habits: $completedHabits",
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Total habits: ${habits.size}",
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Today's completion: $completionPercentage%",
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        LinearProgressIndicator(
            progress = {
                completionPercentage / 100f
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = currentMonth,
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Today: $dayNumber",
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Sun")
            Text(text = "Mon")
            Text(text = "Tue")
            Text(text = "Wed")
            Text(text = "Thu")
            Text(text = "Fri")
            Text(text = "Sat")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        val totalCells =
            (firstDayOfMonth - 1) + daysInMonth

        val totalRows =
            (totalCells + 6) / 7

        for (row in 0 until totalRows) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                for (column in 0..6) {

                    val cellIndex = row * 7 + column

                    val day =
                        cellIndex - (firstDayOfMonth - 1) + 1

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {

                        if (
                            day in 1..daysInMonth &&
                            cellIndex >= firstDayOfMonth - 1
                        ) {

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = day.toString(),
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .background(
                                            color = if (
                                                day == dayNumber.toInt()
                                            ) {
                                                androidx.compose.ui.graphics.Color.LightGray
                                            } else {
                                                androidx.compose.ui.graphics.Color.Transparent
                                            },
                                            shape = CircleShape
                                        )
                                        .padding(8.dp)
                                )

                                if (
                                    day == dayNumber.toInt() &&
                                    completedHabits > 0
                                ) {
                                    Text(
                                        text = "•",
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        HorizontalDivider()

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Habit History",
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (completions.isEmpty()) {

            Text(
                text = "No habit history yet.",
                fontSize = 16.sp
            )

        } else {

            completions
                .map { it.completedDate }
                .distinct()
                .sortedDescending()
                .forEach { date ->

                    Text(
                        text = "✓ $date",
                        fontSize = 16.sp
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )
                }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Complete your habits to start building your history.",
            fontSize = 14.sp
        )
    }
}