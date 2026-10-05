package com.mueez.genzhabittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
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

            _isLoading.value = false
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

            val result = authRepository.loginWithEmail(
                email,
                password
            )

            result.onSuccess { user ->

                _currentUser.value = user

                _successMessage.value =
                    "Login successful."

            }.onFailure { exception ->

                _errorMessage.value =
                    exception.message ?: "Login failed."
            }

            _isLoading.value = false
        }
    }

    fun signInWithGoogle(
        credential: AuthCredential
    ) {
        viewModelScope.launch {

            _isLoading.value = true
            _errorMessage.value = null
            _successMessage.value = null

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
                    exception.message
                        ?: "Google Sign-In failed."
            }

            _isLoading.value = false
        }
    }

    fun setErrorMessage(
        message: String
    ) {
        _errorMessage.value = message
        _successMessage.value = null
        _isLoading.value = false
    }

    fun logout() {

        authRepository.logout()

        _currentUser.value = null

        _errorMessage.value = null

        _successMessage.value =
            "Logged out successfully."
    }

    fun clearMessages() {

        _errorMessage.value = null

        _successMessage.value = null
    }
}