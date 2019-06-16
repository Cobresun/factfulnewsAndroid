package com.cobresun.factfulnewsandroid.repositories.impl

import android.content.Context
import com.cobresun.factfulnewsandroid.CategoryUtils
import com.cobresun.factfulnewsandroid.repositories.UserDataRepository

class SharedPrefsUserDataRepository(private val context: Context) : UserDataRepository {

    val PREFS_NAME = "userPrefs"

    override fun readUserCategories(): Array<String> {
        val settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var categories =  settings.getStringSet("userCategories", CategoryUtils.categories.toSet())
        return categories.toTypedArray()
    }

    override fun writeUserCategories(categories: Array<String>) {
        val settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putStringSet("userCategories", CategoryUtils.categories.toSet())
        editor.apply()
    }
}
