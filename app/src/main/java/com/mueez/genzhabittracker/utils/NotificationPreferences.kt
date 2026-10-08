package com.mueez.genzhabittracker.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "notification_preferences"
)

object NotificationPreferences {

    private val NOTIFICATIONS_ENABLED =
        booleanPreferencesKey("notifications_enabled")

    fun notificationsEnabled(
        context: Context
    ): Flow<Boolean> {

        return context.dataStore.data.map { preferences ->

            preferences[
                NOTIFICATIONS_ENABLED
            ] ?: true
        }
    }

    suspend fun setNotificationsEnabled(
        context: Context,
        enabled: Boolean
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                NOTIFICATIONS_ENABLED
            ] = enabled
        }
    }
}