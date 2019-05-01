package com.cobresun.factfulnewsandroid.backend.api

import com.cobresun.factfulnewsandroid.backend.models.Article

data class FetchResponse(
    val articles: List<Article>
)
