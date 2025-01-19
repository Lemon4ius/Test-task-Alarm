package com.example.test_project_alarm.shared_pref

import android.content.Context

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "photoManagement")

class DataStoreProvider(context: Context) {
    private val dataStore = context.dataStore

    suspend fun saveStringValue(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun readStringValue(key: String): String {
        val dataStoreKey = stringPreferencesKey(key)
        return dataStore.data.first()[dataStoreKey] ?: DataStoreConstant.EMPTY_PATH.name
    }

}

enum class DataStoreConstant {
    PHOTO_PATH,
    EMPTY_PATH
}
