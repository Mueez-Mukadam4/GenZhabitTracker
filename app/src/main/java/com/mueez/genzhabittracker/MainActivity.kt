package com.mueez.genzhabittracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.mueez.genzhabittracker.data.database.DatabaseProvider
import com.mueez.genzhabittracker.presentation.navigation.NavGraph
import com.mueez.genzhabittracker.ui.theme.GenZhabitTrackerTheme
import com.mueez.genzhabittracker.utils.DarkModePreferences
import com.mueez.genzhabittracker.utils.NotificationHelper
import com.mueez.genzhabittracker.utils.NotificationPreferences
import com.mueez.genzhabittracker.utils.NotificationScheduler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DatabaseProvider.initialize(applicationContext)

        NotificationHelper.createNotificationChannel(
            applicationContext
        )

        requestNotificationPermission()

        lifecycleScope.launch {

            val notificationsEnabled =
                NotificationPreferences
                    .notificationsEnabled(applicationContext)
                    .first()

            if (notificationsEnabled) {

                NotificationScheduler.scheduleDailyReminder(
                    applicationContext
                )

            } else {

                NotificationScheduler.cancelDailyReminder(
                    applicationContext
                )
            }
        }

        enableEdgeToEdge()

        setContent {

            val darkModeEnabled by
            DarkModePreferences
                .darkModeEnabled(applicationContext)
                .collectAsState(initial = false)

            GenZhabitTrackerTheme(
                darkTheme = darkModeEnabled
            ) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavGraph()
                }
            }
        }
    }

    private fun requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS
                    ),
                    1001
                )
            }
        }
    }
}