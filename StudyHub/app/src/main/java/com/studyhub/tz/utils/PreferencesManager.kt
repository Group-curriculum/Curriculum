package com.studyhub.tz.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Manager for app preferences using DataStore
 */
class PreferencesManager(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val EDUCATION_LEVEL_KEY = stringPreferencesKey("education_level")
        private val NOTIFICATIONS_ENABLED_KEY = booleanPreferencesKey("notifications_enabled")
        private val FIRST_LAUNCH_KEY = booleanPreferencesKey("first_launch")
    }

    /**
     * Save language preference
     */
    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    /**
     * Get language preference
     */
    val language: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LANGUAGE_KEY] ?: Constants.LANG_ENGLISH
    }

    /**
     * Save education level
     */
    suspend fun saveEducationLevel(level: String) {
        context.dataStore.edit { preferences ->
            preferences[EDUCATION_LEVEL_KEY] = level
        }
    }

    /**
     * Get education level
     */
    val educationLevel: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[EDUCATION_LEVEL_KEY] ?: "O_LEVEL"
    }

    /**
     * Save notification preference
     */
    suspend fun saveNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED_KEY] = enabled
        }
    }

    /**
     * Get notification preference
     */
    val notificationsEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[NOTIFICATIONS_ENABLED_KEY] ?: true
    }

    /**
     * Check if first launch
     */
    val isFirstLaunch: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[FIRST_LAUNCH_KEY] ?: true
    }

    /**
     * Mark first launch as complete
     */
    suspend fun setFirstLaunchComplete() {
        context.dataStore.edit { preferences ->
            preferences[FIRST_LAUNCH_KEY] = false
        }
    }

    /**
     * Clear all preferences
     */
    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
