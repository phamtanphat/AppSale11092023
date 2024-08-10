package com.example.appsale11092023.common

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

object AppSharedPreferences {
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            AppCommon.FILE_NAME_SHARED_PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    private fun edit(context: Context): SharedPreferences.Editor {
<<<<<<< Updated upstream
        return getSharedPreferences(context).edit()
    }

    fun saveData(context: Context, key: String, value: String) {
        edit(context).apply {
=======
        return getSharedPreferences(context = context).edit()
    }

    fun saveData(context: Context, key: String, value: String) {
        edit(context).apply() {
>>>>>>> Stashed changes
            putString(key, value)
            apply()
        }
    }

    fun getString(context: Context, key: String): String {
        return getSharedPreferences(context).getString(key, "").orEmpty()
    }
}