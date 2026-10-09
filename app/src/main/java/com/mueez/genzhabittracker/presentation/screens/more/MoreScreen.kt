package com.mueez.genzhabittracker.presentation.screens.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun MoreScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
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
            text = "More",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Explore more tools to support your journey.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        MoreFeatureCard(
            icon = Icons.Default.Person,
            title = "My Profile",
            description = "View your account and personal information.",
            onClick = {
                navController.navigate("profile")
            }
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        MoreFeatureCard(
            icon = Icons.Default.AutoAwesome,
            title = "Motivational Quotes",
            description = "Get a little motivation whenever you need it.",
            onClick = {
                navController.navigate("quotes")
            }
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        MoreFeatureCard(
            icon = Icons.Default.BarChart,
            title = "Year in Pixels",
            description = "Visualize your progress throughout the year.",
            onClick = {
                navController.navigate("yearinpixels")
            }
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        MoreFeatureCard(
            icon = Icons.Default.Settings,
            title = "Settings",
            description = "Manage your preferences and account.",
            onClick = {
                navController.navigate("settings")
            }
        )
    }
}

@Composable
private fun MoreFeatureCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        TextButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}