package com.example.pomodoro.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class UserPreferencesRepository(private val context : Context) {

    companion object{
        val KEY_FOCUS_TIME = floatPreferencesKey("focus_time")
        val KEY_SHORT_BREAK = floatPreferencesKey("short_break")
        val KEY_LONG_BREAK = floatPreferencesKey("long_break")
    }
    val focusTimeFlow: Flow<Float> = context.dataStore.data
        .map { preferences -> preferences[KEY_FOCUS_TIME] ?: 50f }

    val shortBreakFlow: Flow<Float> = context.dataStore.data
        .map { preferences -> preferences[KEY_SHORT_BREAK] ?: 10f }

    val longBreakFlow: Flow<Float> = context.dataStore.data
        .map { preferences -> preferences[KEY_LONG_BREAK] ?: 30f }

    suspend fun saveFocusTime(time: Float) {
        context.dataStore.edit { preferences ->
            preferences[KEY_FOCUS_TIME] = time
        }
    }

    suspend fun saveShortBreak(time: Float) {
        context.dataStore.edit { preferences ->
            preferences[KEY_SHORT_BREAK] = time
        }
    }

    suspend fun saveLongBreak(time: Float) {
        context.dataStore.edit { preferences ->
            preferences[KEY_LONG_BREAK] = time
        }
    }
}