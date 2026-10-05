package com.mueez.genzhabittracker.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInHelper {

    suspend fun getGoogleCredential(
        context: Context
    ): AuthCredential {

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(
                context.getString(
                    com.mueez.genzhabittracker.R.string.default_web_client_id
                )
            )
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(context)

        val result = credentialManager.getCredential(
            context = context,
            request = request
        )

        val googleCredential =
            GoogleIdTokenCredential.createFrom(
                result.credential.data
            )

        return GoogleAuthProvider.getCredential(
            googleCredential.idToken,
            null
        )
    }
}