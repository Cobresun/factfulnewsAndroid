package com.cobresun.factfulnewsandroid.repositories

import com.cobresun.factfulnewsandroid.tabs.Article
import com.cobresun.factfulnewsandroid.tabs.Category
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getArticles(tabCategory: Category): List<Article> {
        return apiService.fetchArticles(tabCategory.value).articles
    }
}
