package com.ximendes.sumtwitter.data.repository.user

interface UserRepository {

    fun getString(key: String): String
    fun saveString(key: String, value: String)
    fun hasUserLogged(): Boolean
}