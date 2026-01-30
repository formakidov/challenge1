package com.formakidov.challenge1.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "saved_albums")

class SavedAlbumManager(private val context: Context) {
    private val SAVED_IDS_KEY = stringSetPreferencesKey("saved_ids")

    val savedIds: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[SAVED_IDS_KEY] ?: emptySet()
        }

    suspend fun toggleSave(albumId: String) {
        context.dataStore.edit { preferences ->
            val currentIds = preferences[SAVED_IDS_KEY] ?: emptySet()
            if (currentIds.contains(albumId)) {
                preferences[SAVED_IDS_KEY] = currentIds - albumId
            } else {
                preferences[SAVED_IDS_KEY] = currentIds + albumId
            }
        }
    }
}
