package com.cobresun.factfulnewsandroid.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.cobresun.factfulnewsandroid.CategoryUtils

class UserPreferences(
    private val sharedPreferences: SharedPreferences
) {

    fun userCategories(): List<String> {
        return CategoryUtils.categories.filter {
            sharedPreferences.getBoolean("category_$it", true)
        }
    }

    fun writeUserCategories(category: String, enabled: Boolean) {
        sharedPreferences.edit { putBoolean(category, enabled) }
    }

    fun userReadTime() = sharedPreferences.getInt("read_time", 30)
}
