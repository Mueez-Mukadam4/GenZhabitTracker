package com.mueez.genzhabittracker.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.darkModeDataStore by preferencesDataStore(
    name = "dark_mode_preferences"
)

object DarkModePreferences {

    private val DARK_MODE_ENABLED =
        booleanPreferencesKey("dark_mode_enabled")

    fun darkModeEnabled(
        context: Context
    ): Flow<Boolean> {

        return context.darkModeDataStore.data.map { preferences ->

            preferences[
                DARK_MODE_ENABLED
            ] ?: false
        }
    }

    suspend fun setDarkModeEnabled(
        context: Context,
        enabled: Boolean
    ) {

        context.darkModeDataStore.edit { preferences ->

            preferences[
                DARK_MODE_ENABLED
            ] = enabled
        }
    }
}