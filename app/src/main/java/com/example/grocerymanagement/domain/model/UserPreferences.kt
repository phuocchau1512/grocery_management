package com.example.grocerymanagement.domain.model

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

// DataStore instance
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

