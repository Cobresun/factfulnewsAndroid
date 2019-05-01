package com.cobresun.factfulnewsandroid.ui.activities

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobresun.factfulnewsandroid.R
import com.cobresun.factfulnewsandroid.backend.api.ApiService
import com.cobresun.factfulnewsandroid.backend.api.FetchResponse
import com.cobresun.factfulnewsandroid.backend.models.Article
import com.cobresun.factfulnewsandroid.ui.adapters.ArticlesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://factfulnews.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.fetchAllArticles().enqueue(object: Callback<FetchResponse>{
            override fun onResponse(call: Call<FetchResponse>, response: Response<FetchResponse>) {
                showArticles(response.body()!!.articles)
            }

            override fun onFailure(call: Call<FetchResponse>, t: Throwable) {
                Timber.d(t.toString()) // Log the failure.
            }
        })
    }

    private fun showArticles(articles: List<Article>) {
        val itemOnClick: (View, Int, Int) -> Unit = { _, position, _ ->
            // The URL belonging to the selected article.
            val articleUrl = articles[position].url
            // Initialize the Custom Tabs Intent Builder.
            val ctBuilder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            // Show the website's title in the Custom Tab.
            ctBuilder.setShowTitle(true)
            // Set the toolbar's color to be the app's primary color.
            ctBuilder.setToolbarColor(ResourcesCompat.getColor(resources,
                R.color.colorPrimary, null))
            // Build the Custom Tabs intent.
            val ctIntent: CustomTabsIntent = ctBuilder.build()
            // Parse the article URL as a Uri, and then launch the
            // intent.
            ctIntent.launchUrl(this@MainActivity, Uri.parse(articleUrl))
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter =
                ArticlesAdapter(this@MainActivity, articles, itemOnClick)
        }
    }
}
