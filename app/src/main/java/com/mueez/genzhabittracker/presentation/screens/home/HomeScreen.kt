
package com.mueez.genzhabittracker.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Button
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController
) {

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

