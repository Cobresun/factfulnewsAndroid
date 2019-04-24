package com.example.factfulnewsandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "FactfulnewsTag"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://factfulnews.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.fetchAllArticles().enqueue(object: Callback<ArticlesFetchResponse>{
            override fun onResponse(call: Call<ArticlesFetchResponse>, response: Response<ArticlesFetchResponse>) {
                showData(response.body()!!.content)
            }

            override fun onFailure(call: Call<ArticlesFetchResponse>, t: Throwable) {
                Log.d(TAG, t.toString())
            }
        })
    }

    private fun showData(articles: List<Article>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ArticlesAdapter(articles)
        }
    }
}
