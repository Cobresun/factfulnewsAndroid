package com.cobresun.factfulnewsandroid.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.cobresun.factfulnewsandroid.CategoryUtils

class SharedPrefsUserDataRepository(
    private val sharedPreferences: SharedPreferences,
    private val defaultSharedPreferences: SharedPreferences
) {

    fun readUserCategories(): List<String> {
        return CategoryUtils.categories.filter { sharedPreferences.getBoolean("category_$it", true) }
    }

    fun writeUserCategories(category: String, enabled: Boolean) {
        sharedPreferences.edit { putBoolean(category, enabled) }
    }

    fun readUserReadTime(): Int {
        return defaultSharedPreferences.getInt("read_time", 30)
    }
}
