package com.cobresun.factfulnewsandroid.backend.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/{category}")
    fun fetchArticles(@Path("category")category: String): Call<FetchResponse>
}
