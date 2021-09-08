package com.cobresun.factfulnewsandroid.repositories

import com.cobresun.factfulnewsandroid.tabs.Article
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FetchResponse(
    val articles: List<Article>
)
