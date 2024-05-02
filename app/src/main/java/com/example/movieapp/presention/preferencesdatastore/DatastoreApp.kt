package com.example.movieapp.presention.preferencesdatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


const val PREFERENCES_NAME="is_first_time"
val Context.dataStore:DataStore<Preferences> by preferencesDataStore("setting")

class DatastoreApp @Inject constructor(context: Context) {
    private val onBoardingScreenKey = booleanPreferencesKey(PREFERENCES_NAME)
    private val dataStore = context.dataStore


    suspend fun save(show: Boolean) {
        dataStore.edit {
            it[onBoardingScreenKey] = show
        }

    }

    suspend fun read(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[onBoardingScreenKey] ?: false
        }


    }
}