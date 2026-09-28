package com.mueez.genzhabittracker.presentation.screens.splash

import androidx.compose.foundation.Image
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
import com.mueez.genzhabittracker.R
import com.mueez.genzhabittracker.ui.theme.GenZRed


@Composable
fun SplashScreen(){
    Column(
        modifier=Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment= Alignment.CenterHorizontally
    ) {
        Image(
            painter= painterResource(id= R.drawable.genzhabit_logo),
            contentDescription="GenZhabit Tracker Logo",
            modifier=Modifier.size(140.dp)
        )
        Spacer(

            modifier =Modifier.height(24.dp)
        )
        Text(
            text="GenZhabitTracker",
            color=GenZRed,
            fontSize=28.sp
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Text(
            text="Build habits. Track progress. Become better.",
        )
    }
}