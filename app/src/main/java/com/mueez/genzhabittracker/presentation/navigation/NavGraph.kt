package com.mueez.genzhabittracker.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mueez.genzhabittracker.data.database.DatabaseProvider
import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.model.HabitCompletion
import com.mueez.genzhabittracker.data.repository.AuthRepository
import com.mueez.genzhabittracker.data.repository.GoogleSignInHelper
import com.mueez.genzhabittracker.data.repository.HabitRepository
import com.mueez.genzhabittracker.presentation.components.GenZBottomNavigationBar
import com.mueez.genzhabittracker.presentation.screens.auth.LoginScreen
import com.mueez.genzhabittracker.presentation.screens.auth.RegisterScreen
import com.mueez.genzhabittracker.presentation.screens.calendar.CalendarScreen
import com.mueez.genzhabittracker.presentation.screens.edit.EditHabitScreen
import com.mueez.genzhabittracker.presentation.screens.habit.HabitScreen
import com.mueez.genzhabittracker.presentation.screens.home.HomeScreen
import com.mueez.genzhabittracker.presentation.screens.more.MoreScreen
import com.mueez.genzhabittracker.presentation.screens.onboarding.OnboardingScreen
import com.mueez.genzhabittracker.presentation.screens.quotes.QuotesScreen
import com.mueez.genzhabittracker.presentation.screens.settings.GuidelinesScreen
import com.mueez.genzhabittracker.presentation.screens.settings.PrivacyScreen
import com.mueez.genzhabittracker.presentation.screens.settings.SettingsScreen
import com.mueez.genzhabittracker.presentation.screens.settings.TermsScreen
import com.mueez.genzhabittracker.presentation.screens.splash.SplashScreen
import com.mueez.genzhabittracker.presentation.screens.todo.TodoScreen
import com.mueez.genzhabittracker.presentation.screens.yearinpixels.YearInPixelsScreen
import com.mueez.genzhabittracker.viewmodel.AuthViewModel
import com.mueez.genzhabittracker.viewmodel.AuthViewModelFactory
import com.mueez.genzhabittracker.viewmodel.HabitViewModel
import com.mueez.genzhabittracker.viewmodel.HabitViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    val context =
        androidx.compose.ui.platform.LocalContext.current

    val coroutineScope =
        rememberCoroutineScope()

    val googleSignInHelper =
        remember {
            GoogleSignInHelper()
        }

    val authRepository =
        remember {
            AuthRepository()
        }

    val authViewModel: AuthViewModel =
        viewModel(
            factory = AuthViewModelFactory(
                authRepository
            )
        )

    val currentUser by
    authViewModel.currentUser.collectAsState()

    val navBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry?.destination?.route

    val bottomNavigationRoutes = setOf(
        "home",
        "habit",
        "calendar",
        "todo"
    )

    val showBottomBar =
        currentRoute in bottomNavigationRoutes

    LaunchedEffect(currentUser) {

        if (
            currentUser != null &&
            currentRoute == "login"
        ) {
            navController.navigate("home") {

                popUpTo("login") {
                    inclusive = true
                }

                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.weight(1f)
        ) {

            composable("login") {

                val isLoading by
                authViewModel.isLoading.collectAsState()

                val errorMessage by
                authViewModel.errorMessage.collectAsState()

                val successMessage by
                authViewModel.successMessage.collectAsState()

                LoginScreen(
                    onLoginClick = { email, password ->

                        authViewModel.login(
                            email = email,
                            password = password
                        )
                    },
                    onRegisterClick = {

                        authViewModel.clearMessages()

                        navController.navigate("register")
                    },
                    onGoogleSignInClick = {

                        authViewModel.clearMessages()

                        coroutineScope.launch {

                            try {

                                val credential =
                                    googleSignInHelper
                                        .getGoogleCredential(context)

                                authViewModel.signInWithGoogle(
                                    credential
                                )

                            } catch (exception: Exception) {

                                authViewModel.clearMessages()

                                authViewModel.setErrorMessage(
                                    exception.message
                                        ?: "Google Sign-In failed."
                                )
                            }
                        }
                    },
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    successMessage = successMessage
                )
            }

            composable("register") {

                val isLoading by
                authViewModel.isLoading.collectAsState()

                val errorMessage by
                authViewModel.errorMessage.collectAsState()

                val successMessage by
                authViewModel.successMessage.collectAsState()

                RegisterScreen(
                    onRegisterClick = { email, password ->

                        authViewModel.register(
                            email = email,
                            password = password
                        )
                    },
                    onBackClick = {

                        authViewModel.clearMessages()

                        navController.popBackStack()
                    },
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    successMessage = successMessage
                )
            }

            composable("splash") {
                SplashScreen(navController)
            }

            composable("onboarding") {
                OnboardingScreen(navController)
            }

            composable("home") {

                HomeScreen(
                    navController = navController,
                    onLogoutClick = {

                        authViewModel.logout()

                        navController.navigate("login") {

                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable("habit") {
                HabitScreen(navController)
            }

            composable("calendar") {

                val habitDao =
                    DatabaseProvider.database.habitDao()

                val habitCompletionDao =
                    DatabaseProvider.database.habitCompletionDao()

                val habitRepository =
                    HabitRepository(
                        habitDao,
                        habitCompletionDao
                    )

                val factory =
                    HabitViewModelFactory(habitRepository)

                val habitViewModel: HabitViewModel =
                    viewModel(factory = factory)

                var habits by remember {
                    mutableStateOf<List<Habit>>(emptyList())
                }

                var completions by remember {
                    mutableStateOf<List<HabitCompletion>>(emptyList())
                }

                LaunchedEffect(Unit) {

                    habitViewModel.getAllHabits { result ->

                        habits = result

                        result.forEach { habit ->

                            habitViewModel
                                .getCompletionsForHabit(habit.id) { result ->

                                    completions = completions + result
                                }
                        }
                    }
                }

                CalendarScreen(
                    navController = navController,
                    habits = habits,
                    completions = completions
                )
            }

            composable("yearinpixels") {
                YearInPixelsScreen(navController)
            }

            composable("todo") {
                TodoScreen(navController)
            }

            composable("quotes") {
                QuotesScreen(navController)
            }

            composable("settings") {

                SettingsScreen(
                    navController = navController,
                    onLogoutClick = {

                        authViewModel.logout()

                        navController.navigate("login") {

                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable("privacy") {
                PrivacyScreen(navController)
            }

            // NEW: Terms & Conditions
            composable("terms") {
                TermsScreen(navController)
            }

            // NEW: App Guidelines
            composable("guidelines") {
                GuidelinesScreen(navController)
            }

            composable("more") {
                MoreScreen(navController)
            }

            composable("edit/{habitId}") { backStackEntry ->

                val habitId =
                    backStackEntry.arguments
                        ?.getString("habitId")
                        ?.toIntOrNull()

                var selectedHabit by remember {
                    mutableStateOf<Habit?>(null)
                }

                if (habitId != null) {

                    val habitDao =
                        DatabaseProvider.database.habitDao()

                    val habitCompletionDao =
                        DatabaseProvider.database.habitCompletionDao()

                    val habitRepository =
                        HabitRepository(
                            habitDao,
                            habitCompletionDao
                        )

                    val factory =
                        HabitViewModelFactory(habitRepository)

                    val habitViewModel: HabitViewModel =
                        viewModel(factory = factory)

                    LaunchedEffect(habitId) {

                        habitViewModel.getHabitById(habitId) { habit ->

                            selectedHabit = habit
                        }
                    }

                    selectedHabit?.let { habit ->

                        EditHabitScreen(
                            navController = navController,
                            habit = habit
                        )
                    }
                }
            }
        }

        if (showBottomBar) {

            GenZBottomNavigationBar(
                currentRoute = currentRoute,
                onItemClick = { route ->

                    if (route == "more") {

                        navController.navigate("more") {

                            launchSingleTop = true
                        }

                    } else {

                        navController.navigate(route) {

                            popUpTo("home") {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}