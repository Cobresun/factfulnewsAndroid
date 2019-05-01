package com.cobresun.factfulnewsandroid.backend.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/all")
    fun fetchAllArticles(): Call<FetchResponse>
}
