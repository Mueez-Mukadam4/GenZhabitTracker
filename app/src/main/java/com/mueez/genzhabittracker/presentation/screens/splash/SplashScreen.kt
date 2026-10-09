package com.mueez.genzhabittracker.presentation.screens.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.mueez.genzhabittracker.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }

    var showTitle by remember {
        mutableStateOf(false)
    }

    var showTagline by remember {
        mutableStateOf(false)
    }

    val logoScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.55f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "Logo Scale"
    )

    val logoAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 700
        ),
        label = "Logo Alpha"
    )

    val logoRotation by animateFloatAsState(
        targetValue = if (startAnimation) 0f else -12f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "Logo Rotation"
    )

    val titleAlpha by animateFloatAsState(
        targetValue = if (showTitle) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500
        ),
        label = "Title Alpha"
    )

    val titleOffset by animateFloatAsState(
        targetValue = if (showTitle) 0f else 18f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "Title Offset"
    )

    val taglineAlpha by animateFloatAsState(
        targetValue = if (showTagline) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500
        ),
        label = "Tagline Alpha"
    )

    val taglineOffset by animateFloatAsState(
        targetValue = if (showTagline) 0f else 12f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "Tagline Offset"
    )

    LaunchedEffect(Unit) {
        startAnimation = true

        delay(700)
        showTitle = true

        delay(300)
        showTagline = true

        delay(1000)

        // Check the existing Firebase authentication session.
        val currentUser =
            FirebaseAuth.getInstance().currentUser

        val destination = when {
            currentUser != null -> "home"
            else -> "onboarding"
        }

        navController.navigate(destination) {
            popUpTo("splash") {
                inclusive = true
            }

            launchSingleTop = true
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
                .size(150.dp)
                .graphicsLayer {
                    scaleX = logoScale
                    scaleY = logoScale
                    rotationZ = logoRotation
                }
                .alpha(logoAlpha)
                .clip(
                    RoundedCornerShape(24.dp)
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(24.dp)
                )
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "GenZhabit Tracker",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .alpha(titleAlpha)
                .graphicsLayer {
                    translationY = titleOffset
                }
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Build habits. Track progress. Become better.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .alpha(taglineAlpha)
                .graphicsLayer {
                    translationY = taglineOffset
                }
        )
    }
}