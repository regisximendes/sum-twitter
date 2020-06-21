package com.ximendes.sumtwitter.data.repository.user

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth

class UserRepositoryImpl(private val context: Context) : UserRepository {

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "").orEmpty()
    }

    override fun saveString(key: String, value: String) {
        return sharedPreferences.edit().putString(key, value ?: "").apply()
    }

    override fun hasUserLogged(): Boolean {
        val firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.currentUser != null
    }

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}