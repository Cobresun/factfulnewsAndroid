package com.cobresun.factfulnewsandroid


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobresun.factfulnewsandroid.backend.api.ApiService
import com.cobresun.factfulnewsandroid.backend.api.FetchResponse
import com.cobresun.factfulnewsandroid.backend.models.Article
import com.cobresun.factfulnewsandroid.ui.activities.MainActivity
import com.cobresun.factfulnewsandroid.ui.adapters.ArticlesAdapter
import kotlinx.android.synthetic.main.tab_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class TabFragment(title: String) : Fragment() {
    var tabTitle = title
    var articles : List<Article>? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(activity != null){               // Afterwards keep displaying already initialized articles
            articles?.let { showArticles(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://factfulnews.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Initialize and display articles from api response on first run only
        if(articles == null) {
            val api = retrofit.create(ApiService::class.java)
            api.fetchArticles(tabTitle).enqueue(object : Callback<FetchResponse> {
                override fun onResponse(call: Call<FetchResponse>, response: Response<FetchResponse>) {
                    if (response.body() != null) {
                        response.body()?.let { articles = it.articles }
                        if (recyclerView != null) {
                            articles?.let { showArticles(it) }
                        }
                    } else {
                        Toast.makeText(context, "Error Fetching response!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<FetchResponse>, t: Throwable) {
                    Timber.d(t.toString())
                }
            })
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_fragment, container, false)
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
            ctBuilder.setToolbarColor(
                ResourcesCompat.getColor(resources,
                    R.color.colorPrimary, null))
            // Build the Custom Tabs intent.
            val ctIntent: CustomTabsIntent = ctBuilder.build()
            // Parse the article URL as a Uri, and then launch the
            // intent.
            ctIntent.launchUrl(activity, Uri.parse(articleUrl))
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ArticlesAdapter(context, articles, itemOnClick)
        }
    }
}
