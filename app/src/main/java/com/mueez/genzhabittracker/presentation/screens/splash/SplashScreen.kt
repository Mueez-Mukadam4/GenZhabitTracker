
package com.mueez.genzhabittracker.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mueez.genzhabittracker.R
import com.mueez.genzhabittracker.ui.theme.GenZRed
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("onboarding"){
            popUpTo("splash"){
                inclusive=true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(
            painter = painterResource(
                id = R.drawable.genzhabit_logo
            ),
            contentDescription = "GenZhabit Tracker Logo",
            modifier = Modifier
                .size(140.dp)
                .border(
                    width = 5.dp,
                    color = Color.Black
                )
                .border(
                    width = 2.dp,
                    color = GenZRed
                )
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )


        Text(
            text = "GenZhabitTracker",
            color = GenZRed,
            fontSize = 28.sp,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    blurRadius = 6f
                )
            )
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Build habits. Track progress. Become better."
        )





    }
}

