package com.cobresun.factfulnewsandroid.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.cobresun.factfulnewsandroid.tabs.Category
import javax.inject.Inject

class UserPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun userCategories(): List<Category> {
        return Category.values().filter {
            sharedPreferences.getBoolean("category_${it.value}", true)
        }
    }

    fun writeUserCategories(category: String, enabled: Boolean) {
        sharedPreferences.edit { putBoolean(category, enabled) }
    }

    fun userReadTime() = sharedPreferences.getInt("read_time", 30)
}
