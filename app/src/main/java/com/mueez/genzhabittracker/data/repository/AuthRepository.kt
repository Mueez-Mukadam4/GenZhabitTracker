package com.mueez.genzhabittracker.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val firebaseAuth: FirebaseAuth =
        FirebaseAuth.getInstance()

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    suspend fun registerWithEmail(
        email: String,
        password: String
    ): Result<FirebaseUser> {

        return try {
            val result = firebaseAuth
                .createUserWithEmailAndPassword(
                    email,
                    password
                )
                .await()

            val user = result.user

            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(
                    Exception("Registration failed. User is null.")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginWithEmail(
        email: String,
        password: String
    ): Result<FirebaseUser> {

        return try {
            val result = firebaseAuth
                .signInWithEmailAndPassword(
                    email,
                    password
                )
                .await()

            val user = result.user

            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(
                    Exception("Login failed. User is null.")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signInWithGoogleCredential(
        credential: AuthCredential
    ): Result<FirebaseUser> {

        return try {
            val result = firebaseAuth
                .signInWithCredential(credential)
                .await()

            val user = result.user

            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(
                    Exception("Google Sign-In failed. User is null.")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateDisplayName(
        displayName: String
    ): Result<FirebaseUser> {

        return try {
            val user = firebaseAuth.currentUser
                ?: throw Exception("Please sign in to update your profile.")

            val profileUpdates =
                UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName.trim())
                    .build()

            user.updateProfile(profileUpdates).await()


            user.reload().await()

            val updatedUser = firebaseAuth.currentUser
                ?: throw Exception("Unable to retrieve your updated profile.")

            Result.success(updatedUser)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}