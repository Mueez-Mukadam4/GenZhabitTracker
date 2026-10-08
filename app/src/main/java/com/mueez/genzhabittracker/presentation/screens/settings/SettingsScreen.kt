package com.mueez.genzhabittracker.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.mueez.genzhabittracker.utils.DarkModePreferences
import com.mueez.genzhabittracker.utils.NotificationPreferences
import com.mueez.genzhabittracker.utils.NotificationScheduler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController,
    onLogoutClick: () -> Unit
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val currentUser =
        FirebaseAuth.getInstance().currentUser

    val userName =
        currentUser?.displayName
            ?.takeIf { it.isNotBlank() }
            ?: "User"

    val userEmail =
        currentUser?.email
            ?: "Not available"

    var notificationsEnabled by remember {
        mutableStateOf(true)
    }

    var darkModeEnabled by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        notificationsEnabled =
            NotificationPreferences
                .notificationsEnabled(context)
                .first()

        darkModeEnabled =
            DarkModePreferences
                .darkModeEnabled(context)
                .first()
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
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Settings",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Manage your account and app preferences.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        SettingsSectionTitle("Account")

        Spacer(
            modifier = Modifier.height(10.dp)
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
                    text = userName,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = userEmail,
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        SettingsSectionTitle("Appearance")

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Dark Mode",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Use a darker color theme.",
                        fontSize = 13.sp,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { enabled ->
                        darkModeEnabled = enabled

                        coroutineScope.launch {
                            DarkModePreferences
                                .setDarkModeEnabled(
                                    context,
                                    enabled
                                )
                        }
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        SettingsSectionTitle("Notifications")

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Daily habit reminders",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text =
                            "Receive a daily reminder at 8:00 PM.",
                        fontSize = 13.sp,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { enabled ->
                        notificationsEnabled = enabled

                        if (enabled) {
                            NotificationScheduler
                                .scheduleDailyReminder(
                                    context
                                )
                        } else {
                            NotificationScheduler
                                .cancelDailyReminder(
                                    context
                                )
                        }

                        coroutineScope.launch {
                            NotificationPreferences
                                .setNotificationsEnabled(
                                    context,
                                    enabled
                                )
                        }
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        SettingsSectionTitle("Privacy")

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("privacy")
                },
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
                    text = "Privacy",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color =
                        MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text =
                        "Your habits and progress are stored locally and synced securely when cloud sync is available.",
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        SettingsSectionTitle("About Developer")

        Spacer(
            modifier = Modifier.height(10.dp)
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
                    text = "Mueez Mukadam",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color =
                        MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Android Developer",
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text =
                        "I am a Computer Science graduate focused on native Android development with Kotlin and Jetpack Compose. I enjoy building practical, privacy-focused mobile applications and continuously improving my skills through real-world projects.",
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text =
                        "GenZhabit Tracker is a privacy-first habit tracking project built to encourage consistency, progress, and personal growth.",
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                HorizontalDivider()

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "Version 1.0",
                    fontSize = 13.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {
                onLogoutClick()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Logout"
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}

@Composable
private fun SettingsSectionTitle(
    title: String
) {
    Text(
        text = title,
        fontSize = 19.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground
    )
}