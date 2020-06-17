package com.ximendes.sumtwitter.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object PreferencesHelper {

    private lateinit var sharedPreferences: SharedPreferences

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun saveString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value ?: "").apply()

    }

    fun initPreference(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }
}