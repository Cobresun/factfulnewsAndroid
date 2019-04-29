package com.cobresun.factfulnewsandroid

data class Article(
    val title: String,
    val urlToImage: String,
    val snippet: String,
    val index: Int,
    val url: String,
    val sentiment: String
)