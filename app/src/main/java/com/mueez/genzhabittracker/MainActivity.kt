package com.mueez.genzhabittracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mueez.genzhabittracker.data.database.DatabaseProvider
import com.mueez.genzhabittracker.presentation.navigation.NavGraph
import com.mueez.genzhabittracker.ui.theme.GenZhabitTrackerTheme
import com.mueez.genzhabittracker.utils.NotificationHelper

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DatabaseProvider.initialize(applicationContext)

        NotificationHelper.createNotificationChannel(
            applicationContext
        )

        requestNotificationPermission()

        enableEdgeToEdge()

        setContent {
            GenZhabitTrackerTheme {
                NavGraph()
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
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }
}