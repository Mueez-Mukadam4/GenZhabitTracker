package com.mueez.genzhabittracker.presentation.screens.habit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
fun HabitScreen(
    navController: NavController
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

    var habitName by remember {
        mutableStateOf("")
    }

    var frequency by remember {
        mutableStateOf("Daily")
    }

    var frequencyExpanded by remember {
        mutableStateOf(false)
    }

    var habits by remember {
        mutableStateOf<List<Habit>>(emptyList())
    }

    fun loadHabits() {
        habitViewModel.getAllHabits { result ->
            habits = result
        }
    }

    LaunchedEffect(Unit) {
        loadHabits()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {

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
                    color =
                        MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Text(
            text = "Habits",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color =
                MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Build consistency, one habit at a time.",
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
                    text = "Create a Habit",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color =
                        MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                OutlinedTextField(
                    value = habitName,
                    onValueChange = {
                        habitName = it
                    },
                    label = {
                        Text("Habit name")
                    },
                    placeholder = {
                        Text("e.g. Read 10 pages")
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor =
                            MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor =
                            MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor =
                            MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor =
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedPlaceholderColor =
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor =
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        cursorColor =
                            MaterialTheme.colorScheme.primary
                    ),
                    modifier =
                        Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                TextButton(
                    onClick = {
                        frequencyExpanded = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment =
                            Alignment.CenterVertically,
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Frequency: $frequency",
                            color =
                                MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp
                        )

                        Icon(
                            imageVector =
                                Icons.Default.ArrowDropDown,
                            contentDescription =
                                "Select frequency"
                        )
                    }
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
                    modifier = Modifier.height(8.dp)
                )

                Button(
                    onClick = {
                        if (habitName.isNotBlank()) {

                            val habit =
                                Habit(
                                    name = habitName.trim(),
                                    frequency = frequency
                                )

                            habitViewModel.addHabit(habit) {
                                habitName = ""
                                loadHabits()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "Save Habit"
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment =
                Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Text(
                text = "Your Habits",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color =
                    MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "${habits.size}",
                fontSize = 14.sp,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (habits.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "🌱",
                        fontSize = 32.sp
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "No habits yet",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Create your first habit above.",
                        fontSize = 14.sp,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                items(
                    items = habits,
                    key = { habit ->
                        habit.id
                    }
                ) { habit ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 12.dp
                            ),
                        shape =
                            RoundedCornerShape(18.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    MaterialTheme
                                        .colorScheme
                                        .surfaceVariant
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(16.dp)
                        ) {

                            Row(
                                modifier =
                                    Modifier.fillMaxWidth(),
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Checkbox(
                                    checked =
                                        habit.isCompleted,
                                    onCheckedChange = {
                                            checked ->

                                        val updatedHabit =
                                            habit.copy(
                                                isCompleted =
                                                    checked
                                            )

                                        habits =
                                            habits.map {
                                                if (
                                                    it.id ==
                                                    habit.id
                                                ) {
                                                    updatedHabit
                                                } else {
                                                    it
                                                }
                                            }

                                        habitViewModel
                                            .updateHabit(
                                                updatedHabit
                                            )
                                    }
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(1.dp)
                                )

                                Column(
                                    modifier =
                                        Modifier.weight(1f)
                                ) {

                                    Text(
                                        text =
                                            habit.name,
                                        fontSize = 18.sp,
                                        fontWeight =
                                            FontWeight.Medium,
                                        color =
                                            MaterialTheme
                                                .colorScheme
                                                .onSurface
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(3.dp)
                                    )

                                    Text(
                                        text =
                                            "Frequency: ${habit.frequency}",
                                        fontSize = 13.sp,
                                        color =
                                            MaterialTheme
                                                .colorScheme
                                                .onSurfaceVariant
                                    )
                                }
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(8.dp)
                            )

                            Row(
                                modifier =
                                    Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.End
                            ) {

                                TextButton(
                                    onClick = {
                                        navController.navigate(
                                            "edit/${habit.id}"
                                        )
                                    }
                                ) {
                                    Text("Edit")
                                }

                                TextButton(
                                    onClick = {

                                        habitViewModel
                                            .deleteHabit(
                                                habit
                                            ) {
                                                habits =
                                                    habits.filter {
                                                        it.id !=
                                                                habit.id
                                                    }
                                            }
                                    }
                                ) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}