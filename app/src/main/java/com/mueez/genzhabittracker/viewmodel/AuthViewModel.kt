package com.mueez.genzhabittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.mueez.genzhabittracker.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<FirebaseUser?>(
        authRepository.getCurrentUser()
    )

    val currentUser: StateFlow<FirebaseUser?> =
        _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> =
        _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> =
        _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)

    val successMessage: StateFlow<String?> =
        _successMessage.asStateFlow()

    fun register(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _successMessage.value = null

            try {
                val result = authRepository.registerWithEmail(
                    email,
                    password
                )

                result.onSuccess { user ->
                    _currentUser.value = user
                    _successMessage.value =
                        "Account created successfully."
                }.onFailure { exception ->
                    _errorMessage.value =
                        exception.message ?: "Registration failed."
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _successMessage.value = null

            try {
                val result = authRepository.loginWithEmail(
                    email,
                    password
                )

                result.onSuccess { user ->
                    _currentUser.value = user
                    _successMessage.value = "Login successful."
                }.onFailure { exception ->
                    _errorMessage.value = when (exception) {
                        is FirebaseAuthInvalidCredentialsException,
                        is FirebaseAuthInvalidUserException ->
                            "Incorrect email or password."

                        else ->
                            "Login failed. Please try again."
                    }
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signInWithGoogle(
        credential: AuthCredential
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _successMessage.value = null

            try {
                val result =
                    authRepository.signInWithGoogleCredential(
                        credential
                    )

                result.onSuccess { user ->
                    _currentUser.value = user
                    _successMessage.value =
                        "Google Sign-In successful."
                }.onFailure { exception ->
                    _errorMessage.value =
                        exception.message ?: "Google Sign-In failed."
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Updates the signed-in user's Firebase display name.
     */
    fun updateDisplayName(
        displayName: String
    ) {
        val trimmedName = displayName.trim()

        if (trimmedName.isBlank()) {
            _errorMessage.value = "Please enter your name."
            _successMessage.value = null
            return
        }

        if (trimmedName.length > 50) {
            _errorMessage.value =
                "Name must be 50 characters or fewer."
            _successMessage.value = null
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _successMessage.value = null

            try {
                val result =
                    authRepository.updateDisplayName(trimmedName)

                result.onSuccess { updatedUser ->
                    _currentUser.value = updatedUser
                    _successMessage.value =
                        "Profile updated successfully."
                }.onFailure { exception ->
                    _errorMessage.value =
                        exception.message
                            ?: "Unable to update your profile."
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setErrorMessage(
        message: String
    ) {
        val friendlyMessage = when {
            message.contains("cancelled", ignoreCase = true) ||
                    message.contains("canceled", ignoreCase = true) ||
                    message.contains("user cancelled", ignoreCase = true) ||
                    message.contains("user canceled", ignoreCase = true) ||
                    message.contains("selector", ignoreCase = true) -> {
                "Google Sign-In cancelled."
            }

            else -> message
        }

        _errorMessage.value = friendlyMessage
        _successMessage.value = null
        _isLoading.value = false
    }

    fun logout() {
        authRepository.logout()

        _currentUser.value = null
        _errorMessage.value = null
        _successMessage.value = "Logged out successfully."
    }

    fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }
}