package com.cobresun.factfulnewsandroid.repositories.impl

import android.content.Context
import com.cobresun.factfulnewsandroid.CategoryUtils
import com.cobresun.factfulnewsandroid.repositories.UserDataRepository

class SharedPrefsUserDataRepository(private val context: Context) : UserDataRepository {

    val PREFS_NAME = "userPrefs"

    override fun readUserCategories(): Array<String> {
        val settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        var defaultCategories = CategoryUtils.categories
        var userCategories: MutableList<String> = ArrayList()

        for(category in defaultCategories){
            if(settings.getBoolean("category_$category", true)){
                userCategories.add(category)
            }
        }
        return userCategories.toTypedArray()
    }

    override fun writeUserCategories(category: String, enabled: Boolean) {
        val settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putBoolean(category, enabled)
        editor.apply()
    }
}
