package com.example.appsale11092023

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    private val preferences : SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val TOKEN_KEY = "token_key"
    }

    var token: String?
        get() = preferences.getString(TOKEN_KEY,null)
        set(value) = preferences.edit().putString(TOKEN_KEY, value).apply()
}