package com.example.factfulnewsandroid

data class ArticlesFetchResponse(
    val success: Boolean,
    val content: List<Article>
)