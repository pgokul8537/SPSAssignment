package com.example.spsassignment.network

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.spsassignment.network.PreferencesKeys.FAVORITE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("tv_shows")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {
    val prefs = appContext.getSharedPreferences("tv_shows", Context.MODE_PRIVATE)

    fun getStoredTag(): String {
        return prefs.getString("", "")!!
    }

    fun setFavoriteData(query: String) {
        prefs.edit().putString(FAVORITE, query).apply()
    }
    /*private val dataStore = appContext.dataStore

    suspend fun setFavoriteData(favorite: String) {
        dataStore.edit { settings ->
            settings[FAVORITE] = favorite
        }
    }*/

    /*val favoriteMapList: Flow<MutableMap<String, Boolean>> = dataStore.data.map { preferences ->
        val list = Gson().fromJson<MutableMap<String, Boolean>>(
            preferences[FAVORITE],
            object : TypeToken<MutableMap<String, Boolean>>() {}.type
        )
        return@map if (list.isNullOrEmpty()) mutableMapOf() else list
    }*/

    fun getFavorites(): MutableMap<String, Boolean> {
        val list = Gson().fromJson<MutableMap<String, Boolean>>(
            prefs.getString(FAVORITE, ""),
            object : TypeToken<MutableMap<String, Boolean>>() {}.type
        )
        return if (list.isNullOrEmpty()) mutableMapOf() else list
    }
}

private object PreferencesKeys {
    const val FAVORITE = "favorite"
}