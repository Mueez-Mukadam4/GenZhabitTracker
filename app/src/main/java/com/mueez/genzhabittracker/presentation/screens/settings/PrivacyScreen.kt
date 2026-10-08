package com.mueez.genzhabittracker.presentation.screens.settings

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PrivacyScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
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

        Text(
            text = "Privacy",
            fontSize = 24.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Your Privacy Matters",
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "GenZhabit Tracker is designed with privacy in mind. The app keeps your personal habit information under your control and avoids unnecessary data collection.",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Data Storage",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Your habits, habit completions, and to-do items are stored locally on your device using the app's local database.",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Cloud Sync",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "When you are signed in, supported app data can be synchronized with your account using Firebase cloud services.",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Authentication",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Your account authentication is handled securely through Firebase Authentication. The app does not store your password locally.",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Notifications",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Daily habit reminders are optional. You can enable or disable notifications at any time from Settings.",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "No Ads or Unnecessary Tracking",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "GenZhabit Tracker does not use advertisements or unnecessary tracking to monitor your personal habits.",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Your Control",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "You can stop using cloud synchronization by signing out of your account. Local app data remains on your device unless the app data is cleared or the application is uninstalled.",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "GenZhabit Tracker",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Privacy-first habit tracking.",
            fontSize = 13.sp
        )
    }
}