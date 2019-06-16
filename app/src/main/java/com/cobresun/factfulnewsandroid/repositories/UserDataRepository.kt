package com.cobresun.factfulnewsandroid.repositories

interface UserDataRepository {

    fun readUserCategories(): Array<String>

    fun writeUserCategories(category: String, enabled: Boolean)
}
