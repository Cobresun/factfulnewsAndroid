package com.cobresun.factfulnewsandroid

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobresun.factfulnewsandroid.backend.api.ApiService
import com.cobresun.factfulnewsandroid.backend.api.FetchResponse
import com.cobresun.factfulnewsandroid.backend.models.Article
import com.cobresun.factfulnewsandroid.models.Settings
import com.cobresun.factfulnewsandroid.ui.adapters.ArticlesAdapter
import kotlinx.android.synthetic.main.tab_fragment.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

// TODO: IDK how we wrote this code lol Fragment with parameters yikes - crashes on rotation
class TabFragment(title: String, settings: Settings) : Fragment(), CoroutineScope {

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Coroutine Exception", ":${throwable.localizedMessage}")
    }

    private val tabTitle = title
    private val readTime = settings.readTime
    private var articles: List<Article>? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: This feels like something a ViewModel should do!
        if (activity != null) {
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

        if (articles == null) {
            val apiService = retrofit.create(ApiService::class.java)
            mJob = Job()
            launch(coroutineExceptionHandler) {
                val fetchResponse: FetchResponse = apiService.fetchArticles(tabTitle)
                articles = fetchResponse.articles.filter { it.timeToRead <= readTime }
                articles?.let { showArticles(it) }
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_fragment, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }

    private fun showArticles(articles: List<Article>) {
        val itemOnClick: (View, Int, Int) -> Unit = { _, position, _ ->
            val articleUrl = articles[position].url
            val ctBuilder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            ctBuilder.setShowTitle(true)
            ctBuilder.setToolbarColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.colorPrimary,
                    null
                )
            )
            val ctIntent: CustomTabsIntent = ctBuilder.build()
            ctIntent.launchUrl(requireContext(), Uri.parse(articleUrl))
        }

        recyclerView.apply {
            adapter = ArticlesAdapter(requireContext(), articles, itemOnClick)
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }
}
