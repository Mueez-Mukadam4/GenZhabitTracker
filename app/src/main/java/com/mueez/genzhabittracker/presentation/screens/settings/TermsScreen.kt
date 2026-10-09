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
fun TermsScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
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

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Terms & Conditions",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "GenZhabit Tracker • Version 1.0",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        PolicySection(
            title = "1. Acceptance of Terms",
            body = "By using GenZhabit Tracker, you agree to these Terms & Conditions. If you do not agree, please discontinue using the application."
        )

        PolicySection(
            title = "2. Purpose of the App",
            body = "GenZhabit Tracker helps users organize habits, track progress, manage tasks, and support personal growth. It is intended for personal productivity and informational purposes."
        )

        PolicySection(
            title = "3. Your Account",
            body = "You are responsible for maintaining access to your account and for activities performed through it. Please provide accurate account information and keep your sign-in credentials secure."
        )

        PolicySection(
            title = "4. Your Data",
            body = "The app may store supported information locally on your device and synchronize supported data through Firebase services when available. Please review the Privacy Policy for details about data handling."
        )

        PolicySection(
            title = "5. Acceptable Use",
            body = "You agree not to misuse the application, attempt unauthorized access, interfere with its operation, or use it for unlawful purposes."
        )

        PolicySection(
            title = "6. Personal Responsibility",
            body = "Habit suggestions, quotes, reminders, and progress indicators are provided to support personal organization. The app does not guarantee particular outcomes or replace professional advice."
        )

        PolicySection(
            title = "7. Availability and Changes",
            body = "Features may be updated, changed, suspended, or discontinued to improve the application, address technical issues, or maintain security."
        )

        PolicySection(
            title = "8. Disclaimer",
            body = "The application is provided on an as-available basis. To the extent permitted by applicable law, the developer does not guarantee uninterrupted operation or that the app will always be error-free."
        )

        PolicySection(
            title = "9. Contact",
            body = "For questions about these terms, contact the app developer using the contact details published in the official app listing, when available."
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Last updated: October 2026",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun PolicySection(
    title: String,
    body: String
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
                text = body,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}