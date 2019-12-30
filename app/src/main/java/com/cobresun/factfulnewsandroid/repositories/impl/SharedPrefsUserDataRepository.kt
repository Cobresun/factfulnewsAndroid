package com.cobresun.factfulnewsandroid.repositories.impl

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.cobresun.factfulnewsandroid.CategoryUtils
import com.cobresun.factfulnewsandroid.repositories.UserDataRepository

class SharedPrefsUserDataRepository(private val context: Context) : UserDataRepository {

    private val userPreferences by lazy { context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE) }

    override fun readUserCategories(): Array<String> {
        val defaultCategories = CategoryUtils.categories
        val userCategories: MutableList<String> = ArrayList()

        for (category in defaultCategories) {
            if (userPreferences.getBoolean("category_$category", true)) {
                userCategories.add(category)
            }
        }
        return userCategories.toTypedArray()
    }

    override fun writeUserCategories(category: String, enabled: Boolean) {
        userPreferences.edit { putBoolean(category, enabled) }
    }

    override fun readUserReadTime(): Int {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getInt("read_time", 30)
    }
}
