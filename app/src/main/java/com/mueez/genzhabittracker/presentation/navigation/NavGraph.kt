package com.mueez.genzhabittracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mueez.genzhabittracker.presentation.screens.onboarding.OnboardingScreen
import com.mueez.genzhabittracker.presentation.screens.splash.SplashScreen
import com.mueez.genzhabittracker.presentation.screens.home.HomeScreen
import com.mueez.genzhabittracker.presentation.screens.habit.HabitScreen

@Composable
fun NavGraph(){
    val navController=rememberNavController()
    NavHost(
        navController=navController,
        startDestination="splash"
    ){
        composable("splash"){
            SplashScreen(navController)
        }
        composable("onboarding"){
            OnboardingScreen(navController)
        }
        composable ("home"){
            HomeScreen(navController)
        }
        composable("habit") {
            HabitScreen()
        }
    }
}