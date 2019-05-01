package com.cobresun.factfulnewsandroid


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobresun.factfulnewsandroid.backend.api.ApiService
import com.cobresun.factfulnewsandroid.backend.api.FetchResponse
import com.cobresun.factfulnewsandroid.backend.models.Article
import com.cobresun.factfulnewsandroid.ui.adapters.ArticlesAdapter
import kotlinx.android.synthetic.main.tab_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class TabFragment(index: Int) : Fragment() {
    var tabIndex = index

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://factfulnews.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
        api.fetchArticles(CategoryUtils.categories[tabIndex]).enqueue(object: Callback<FetchResponse> {
            override fun onResponse(call: Call<FetchResponse>, response: Response<FetchResponse>) {
                showArticles(response.body()!!.articles)
            }

            override fun onFailure(call: Call<FetchResponse>, t: Throwable) {
                Timber.d(t.toString()) // Log the failure.
            }
        })
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
