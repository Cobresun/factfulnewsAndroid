package com.cobresun.factfulnewsandroid.repositories

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.cobresun.factfulnewsandroid.CategoryUtils

class SharedPrefsUserDataRepository(private val context: Context) {

    private val userPreferences by lazy { context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE) }

    fun readUserCategories(): List<String> {
        return CategoryUtils.categories.filter { userPreferences.getBoolean("category_$it", true) }
    }

    fun writeUserCategories(category: String, enabled: Boolean) {
        userPreferences.edit { putBoolean(category, enabled) }
    }

    fun readUserReadTime(): Int {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getInt("read_time", 30)
    }
}
