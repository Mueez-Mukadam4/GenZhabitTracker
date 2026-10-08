package com.mueez.genzhabittracker.presentation.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.model.HabitCompletion
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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

    val calendar = Calendar.getInstance()

    val year = calendar.get(
        Calendar.YEAR
    )

    val month = calendar.get(
        Calendar.MONTH
    )

    calendar.set(
        Calendar.DAY_OF_MONTH,
        1
    )

    val firstDayOfMonth = calendar.get(
        Calendar.DAY_OF_WEEK
    )

    val daysInMonth = when (month) {
        Calendar.FEBRUARY -> {
            if (
                year % 400 == 0 ||
                (year % 4 == 0 && year % 100 != 0)
            ) {
                29
            } else {
                28
            }
        }

        Calendar.APRIL,
        Calendar.JUNE,
        Calendar.SEPTEMBER,
        Calendar.NOVEMBER -> 30

        else -> 31
    }

    val completedHabits = habits.count {
        it.isCompleted
    }

    val completionPercentage =
        if (habits.isNotEmpty()) {
            (completedHabits * 100) / habits.size
        } else {
            0
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {

        TextButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text(
                text = "←",
                fontSize = 28.sp,
                color =
                    MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = "Calendar",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color =
                MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Track your consistency over time.",
            fontSize = 14.sp,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surfaceVariant
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "Today",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color =
                        MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = today,
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            text = "Completed",
                            fontSize = 13.sp,
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .onSurfaceVariant
                        )

                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )

                        Text(
                            text =
                                completedHabits.toString(),
                            fontSize = 24.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .onSurface
                        )
                    }

                    Column(
                        horizontalAlignment =
                            Alignment.End
                    ) {
                        Text(
                            text = "Total habits",
                            fontSize = 13.sp,
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .onSurfaceVariant
                        )

                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )

                        Text(
                            text = habits.size.toString(),
                            fontSize = 24.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .onSurface
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text =
                        "$completionPercentage% completed today",
                    fontSize = 14.sp,
                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                LinearProgressIndicator(
                    progress = {
                        completionPercentage / 100f
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surfaceVariant
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.SpaceBetween,
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text = currentMonth,
                        fontSize = 20.sp,
                        fontWeight =
                            FontWeight.SemiBold,
                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurface
                    )

                    Text(
                        text = "Today: $dayNumber",
                        fontSize = 13.sp,
                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    listOf(
                        "S",
                        "M",
                        "T",
                        "W",
                        "T",
                        "F",
                        "S"
                    ).forEach { day ->
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment =
                                Alignment.Center
                        ) {
                            Text(
                                text = day,
                                fontSize = 12.sp,
                                fontWeight =
                                    FontWeight.Medium,
                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                val totalCells =
                    (firstDayOfMonth - 1) +
                            daysInMonth

                val totalRows =
                    (totalCells + 6) / 7

                for (row in 0 until totalRows) {

                    Row(
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        for (column in 0..6) {

                            val cellIndex =
                                row * 7 + column

                            val day =
                                cellIndex -
                                        (firstDayOfMonth - 1) +
                                        1

                            Box(
                                modifier =
                                    Modifier
                                        .weight(1f)
                                        .height(46.dp),
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                if (
                                    day in
                                    1..daysInMonth
                                ) {

                                    val isToday =
                                        day ==
                                                dayNumber
                                                    .toInt()

                                    Box(
                                        modifier =
                                            Modifier
                                                .background(
                                                    color =
                                                        if (isToday) {
                                                            MaterialTheme
                                                                .colorScheme
                                                                .primary
                                                        } else {
                                                            MaterialTheme
                                                                .colorScheme
                                                                .surface
                                                        },
                                                    shape =
                                                        CircleShape
                                                )
                                                .padding(
                                                    9.dp
                                                ),
                                        contentAlignment =
                                            Alignment.Center
                                    ) {

                                        Text(
                                            text =
                                                day.toString(),
                                            fontSize =
                                                14.sp,
                                            fontWeight =
                                                if (isToday) {
                                                    FontWeight.Bold
                                                } else {
                                                    FontWeight.Normal
                                                },
                                            color =
                                                if (isToday) {
                                                    MaterialTheme
                                                        .colorScheme
                                                        .onPrimary
                                                } else {
                                                    MaterialTheme
                                                        .colorScheme
                                                        .onSurface
                                                }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        HorizontalDivider()

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Habit History",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color =
                MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (completions.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme
                            .colorScheme
                            .surfaceVariant
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "📅",
                        fontSize = 30.sp
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "No habit history yet",
                        fontSize = 16.sp,
                        fontWeight =
                            FontWeight.Medium,
                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text =
                            "Complete your habits to start building your history.",
                        fontSize = 13.sp,
                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }
            }

        } else {

            completions
                .map {
                    it.completedDate
                }
                .distinct()
                .sortedDescending()
                .forEach { date ->

                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    bottom = 8.dp
                                ),
                        shape =
                            RoundedCornerShape(14.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    MaterialTheme
                                        .colorScheme
                                        .surfaceVariant
                            )
                    ) {

                        Text(
                            text = "✓  $date",
                            modifier =
                                Modifier.padding(
                                    14.dp
                                ),
                            fontSize = 15.sp,
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .onSurface
                        )
                    }
                }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )
    }
}