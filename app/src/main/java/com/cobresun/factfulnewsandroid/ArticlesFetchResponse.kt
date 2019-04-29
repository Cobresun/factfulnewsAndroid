package com.cobresun.factfulnewsandroid

data class ArticlesFetchResponse(
    val success: Boolean,
    val content: List<Article>
)