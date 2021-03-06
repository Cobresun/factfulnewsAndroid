package com.cobresun.factfulnewsandroid.repositories

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/{category}")
    suspend fun fetchArticles(@Path("category")category: String): FetchResponse
}
