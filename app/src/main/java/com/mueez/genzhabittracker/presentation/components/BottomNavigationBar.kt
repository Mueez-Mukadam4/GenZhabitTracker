package com.mueez.genzhabittracker.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun GenZBottomNavigationBar(
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    val items = listOf(
        BottomNavigationItem(
            "Home",
            Icons.Default.Home,
            "home"
        ),
        BottomNavigationItem(
            "Habits",
            Icons.Default.CheckCircle,
            "habit"
        ),
        BottomNavigationItem(
            "Calendar",
            Icons.Default.CalendarMonth,
            "calendar"
        ),
        BottomNavigationItem(
            "To-Do",
            Icons.Default.Notes,
            "todo"
        ),
        BottomNavigationItem(
            "More",
            Icons.Default.MoreHoriz,
            "more"
        )
    )

    NavigationBar(
        containerColor =
            MaterialTheme.colorScheme.surface
    ) {
        items.forEach { item ->

            val selected =
                currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    onItemClick(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label
                    )
                },
                colors =
                    NavigationBarItemDefaults.colors(
                        selectedIconColor =
                            MaterialTheme
                                .colorScheme
                                .onPrimaryContainer,
                        selectedTextColor =
                            MaterialTheme
                                .colorScheme
                                .primary,
                        indicatorColor =
                            MaterialTheme
                                .colorScheme
                                .primaryContainer,
                        unselectedIconColor =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant,
                        unselectedTextColor =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
            )
        }
    }
}