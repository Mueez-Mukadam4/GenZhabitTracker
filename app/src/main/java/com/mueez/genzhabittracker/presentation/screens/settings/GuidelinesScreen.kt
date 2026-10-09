package com.mueez.genzhabittracker.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GuidelinesScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "App Guidelines",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Small steps. Consistent progress.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        GuidelineCard(
            title = "1. Start Small",
            description = "Choose a manageable number of habits. Begin with realistic goals that fit your daily routine."
        )

        GuidelineCard(
            title = "2. Be Consistent",
            description = "Focus on steady progress rather than perfection. Missing a day does not mean you have failed."
        )

        GuidelineCard(
            title = "3. Track Honestly",
            description = "Mark habits and tasks as completed only when you have actually completed them. This helps keep your progress meaningful."
        )

        GuidelineCard(
            title = "4. Review Your Progress",
            description = "Use your habit history and calendar to reflect on patterns and identify opportunities to improve."
        )

        GuidelineCard(
            title = "5. Use Reminders Wisely",
            description = "Enable daily reminders if they help you. You can turn them off whenever you no longer need them."
        )

        GuidelineCard(
            title = "6. Protect Your Account",
            description = "Keep your sign-in credentials private. Use a secure device and sign out when appropriate on shared devices."
        )

        GuidelineCard(
            title = "7. Understand Cloud Sync",
            description = "Cloud synchronization may require an internet connection and a supported signed-in account. Check that your changes have synced before relying on another device."
        )

        GuidelineCard(
            title = "8. Take Care of Yourself",
            description = "Use the app to support personal organization and healthy routines. It is not a medical, mental-health, or emergency service."
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Your journey, your pace. Build habits that work for you.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun GuidelineCard(
    title: String,
    description: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = description,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}