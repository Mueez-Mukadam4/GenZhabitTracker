
package com.mueez.genzhabittracker.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import com.mueez.genzhabittracker.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OnboardingScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image( painter = painterResource( id = R.drawable.onboarding_logo ), contentDescription = "GenZhabit Tracker Logo", modifier = Modifier.size(120.dp) )
        Spacer( modifier = Modifier.height(24.dp) )

        Text(
            text = "Welcome to GenZhabit Tracker",
            fontSize = 26.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Small steps every day create extraordinary results.",
            fontSize = 14.sp
        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        Button(
            onClick = {
                navController.navigate("home")

        }
        ){
            Text(
                text="Let’s Lock In"
            )
        }
    }
}

