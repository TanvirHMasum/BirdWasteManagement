package com.birdwastemanagement.app.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        val IS_FIRST_TIME = booleanPreferencesKey("is_first_time")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val AUTH_TOKEN = stringPreferencesKey("auth_token")

        val USER_ID = stringPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_PHONE = stringPreferencesKey("user_phone")
        val USER_HOLDING_NO = stringPreferencesKey("user_holding_no")
        val USER_WARD = stringPreferencesKey("user_ward")
        val USER_BALANCE = stringPreferencesKey("user_balance")
    }

    val isFirstTime: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_FIRST_TIME] ?: true
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val authToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN]
    }

    val userProfileFlow: Flow<com.birdwastemanagement.app.domain.model.Customer> = context.dataStore.data.map { preferences ->
        com.birdwastemanagement.app.domain.model.Customer(
            id = preferences[USER_ID] ?: "BWM-00000",
            name = preferences[USER_NAME] ?: "Guest User",
            holdingNo = preferences[USER_HOLDING_NO] ?: "N/A",
            ward = preferences[USER_WARD] ?: "Ward N/A",
            mobile = preferences[USER_PHONE] ?: "N/A",
            totalDue = preferences[USER_BALANCE]?.toDoubleOrNull() ?: 0.0
        )
    }

    suspend fun setFirstTime(isFirstTime: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_FIRST_TIME] = isFirstTime
        }
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
            if (!isLoggedIn) {
                preferences.remove(AUTH_TOKEN)
                preferences.remove(USER_ID)
                preferences.remove(USER_NAME)
                preferences.remove(USER_PHONE)
                preferences.remove(USER_HOLDING_NO)
                preferences.remove(USER_WARD)
                preferences.remove(USER_BALANCE)
            }
        }
    }

    suspend fun setAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }

    suspend fun saveUserData(user: com.birdwastemanagement.app.data.remote.dto.UserDto) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = user.customerUid ?: user.id.toString()
            preferences[USER_NAME] = user.name ?: "Guest User"
            preferences[USER_PHONE] = user.phone ?: ""
            preferences[USER_HOLDING_NO] = user.holdingNo ?: ""
            preferences[USER_WARD] = "Ward ${user.wardId ?: ""}"
            preferences[USER_BALANCE] = user.currentBalance ?: "0.0"
        }
    }
}
