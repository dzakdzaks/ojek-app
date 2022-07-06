package com.dzakdzaks.ojekapp.data.local

import android.content.Context
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Single
class DataPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    var token: String
        get() = sharedPreferences.getString(Key.TOKEN, "").orEmpty()
        set(value) {
            sharedPreferences.edit()
                .putString(Key.TOKEN, value)
                .apply()
        }

    fun remove(key: String) {
        sharedPreferences.edit()
            .remove(key)
            .apply()
    }

    companion object : KoinComponent {
        const val NAME = "main_pref"

        val get: DataPreferences = get()
    }

    object Key {
        const val TOKEN = "token"
    }
}