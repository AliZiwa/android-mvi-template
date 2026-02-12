package com.template.app.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    private object Keys {
        val TOKEN = stringPreferencesKey("session_token")
        val USER_ID = stringPreferencesKey("user_id")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_DISPLAY_NAME = stringPreferencesKey("user_display_name")
    }

    suspend fun saveSession(token: String, userId: String, email: String, displayName: String) {
        dataStore.edit { prefs ->
            prefs[Keys.TOKEN] = token
            prefs[Keys.USER_ID] = userId
            prefs[Keys.USER_EMAIL] = email
            prefs[Keys.USER_DISPLAY_NAME] = displayName
        }
    }

    suspend fun getToken(): String? {
        return dataStore.data.map { it[Keys.TOKEN] }.first()
    }

    suspend fun getUserId(): String? {
        return dataStore.data.map { it[Keys.USER_ID] }.first()
    }

    suspend fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    suspend fun clearSession() {
        dataStore.edit { it.clear() }
    }
}
