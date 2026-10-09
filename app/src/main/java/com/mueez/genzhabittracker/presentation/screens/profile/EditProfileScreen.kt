package com.mueez.genzhabittracker.presentation.screens.profile

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
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mueez.genzhabittracker.viewmodel.AuthViewModel

@Composable
fun EditProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val successMessage by authViewModel.successMessage.collectAsState()

    var displayName by remember {
        mutableStateOf(currentUser?.displayName.orEmpty())
    }

    LaunchedEffect(currentUser?.uid, currentUser?.displayName) {
        displayName = currentUser?.displayName.orEmpty()
    }

    LaunchedEffect(successMessage) {
        if (successMessage == "Profile updated successfully.") {
            navController.popBackStack()
            authViewModel.clearMessages()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            IconButton(
                onClick = {
                    authViewModel.clearMessages()
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Edit Profile",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Update your personal information.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedTextField(
                value = displayName,
                onValueChange = {
                    if (it.length <= 50) {
                        displayName = it
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Display name")
                },
                placeholder = {
                    Text("Enter your name")
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Maximum 50 characters.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = errorMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (successMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = successMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    authViewModel.updateDisplayName(displayName)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(22.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(0.dp))

                    Text(
                        text = "Save Changes",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}