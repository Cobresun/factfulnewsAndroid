package com.cobresun.factfulnewsandroid.tabs

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article(
    val title: String,
    val urlToImage: String,
    val snippet: String,
    val url: String,
    val timeToRead: Int?,
    val sentiment: String
)
