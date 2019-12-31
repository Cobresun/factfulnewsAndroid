package com.cobresun.factfulnewsandroid.repositories

import com.cobresun.factfulnewsandroid.models.FetchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/{category}")
    suspend fun fetchArticles(@Path("category")category: String): FetchResponse
}
