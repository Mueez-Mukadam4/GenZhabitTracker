
package com.mueez.genzhabittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mueez.genzhabittracker.data.database.DatabaseProvider
import com.mueez.genzhabittracker.presentation.navigation.NavGraph
import com.mueez.genzhabittracker.ui.theme.GenZhabitTrackerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DatabaseProvider.initialize(applicationContext)

        enableEdgeToEdge()

        setContent {
            GenZhabitTrackerTheme {
                NavGraph()
            }
        }
    }
}

