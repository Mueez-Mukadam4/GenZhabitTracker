
package com.mueez.genzhabittracker.presentation.screens.habit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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


import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitScreen() {

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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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
        ExposedDropdownMenuBox(
            expanded = frequencyExpanded,
            onExpandedChange = {
                frequencyExpanded = !frequencyExpanded
            }
        ) {

            OutlinedTextField(
                value = frequency,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(
                        text = "Frequency",
                        color = Color.Black
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = frequencyExpanded
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    cursorColor = Color.Black
                ),
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
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
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {
                // Save habit logic will be added later
            }
        ) {
            Text(
                text = "Save Habit",
                color = Color.White
            )
        }
    }
}

