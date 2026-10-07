package com.mueez.genzhabittracker.presentation.screens.yearinpixels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mueez.genzhabittracker.utils.YearPixelCalculator

@Composable
fun YearInPixelsScreen() {

    val totalDays =
        YearPixelCalculator.getTotalDaysInYear()

    val currentDay =
        YearPixelCalculator.getDayOfYear()

    val days =
        (1..totalDays).toList()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Year in Pixels",
            fontSize = 24.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Every day is a small pixel in your year.",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Today: Day $currentDay / $totalDays",
                fontSize = 14.sp
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            items(days) { day ->

                val pixelColor =
                    when {
                        day < currentDay ->
                            Color(0xFFBDBDBD)

                        day == currentDay ->
                            Color(0xFF2196F3)

                        else ->
                            Color(0xFFE8E8E8)
                    }

                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = pixelColor,
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = day.toString(),
                        fontSize = 9.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}