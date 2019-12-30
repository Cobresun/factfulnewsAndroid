package com.cobresun.factfulnewsandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobresun.factfulnewsandroid.backend.api.ApiService
import com.cobresun.factfulnewsandroid.backend.api.FetchResponse
import com.cobresun.factfulnewsandroid.backend.models.Article
import kotlinx.android.synthetic.main.tab_fragment.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class TabFragment : Fragment(), CoroutineScope {

    companion object {
        private const val TAB_TITLE = "tab_title"
        private const val READ_TIME = "read_time"

        fun newInstance(title: String, readTime: Int) = TabFragment().apply {
            arguments = bundleOf(
                TAB_TITLE to title,
                READ_TIME to readTime
            )
        }
    }

    private val tabTitle: String by lazy { arguments?.getString(TAB_TITLE) ?: "" }
    private val readTime: Int by lazy { arguments?.getInt(READ_TIME) ?: 0 }

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Coroutine Exception", ":$throwable")
    }

    private val apiService = Retrofit
        .Builder()
        .baseUrl("https://factfulnews.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private val articleClickListener: (Article) -> Unit = {
        CustomTabsIntent
            .Builder()
            .setShowTitle(true)
            .setToolbarColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
            .build()
            .launchUrl(requireContext(), Uri.parse(it.url))
    }

    private val articleShareClickListener: (Article) -> Unit = {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, it.title + "\n\n" + it.url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        requireContext().startActivity(shareIntent)
    }

    private var articles: List<Article>? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        articles?.let { showArticles(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (articles == null) {
            mJob = Job()
            launch(coroutineExceptionHandler) {
                val fetchResponse: FetchResponse = apiService.fetchArticles(tabTitle)
                articles = fetchResponse.articles.filter { it.timeToRead <= readTime }
                articles?.let { showArticles(it) }
            }
        }
        return inflater.inflate(R.layout.tab_fragment, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }

    private fun showArticles(articles: List<Article>) {
        recyclerView.apply {
            adapter = ArticlesAdapter(
                context = requireContext(),
                articles = articles,
                clickListener = articleClickListener,
                shareClickListener = articleShareClickListener
            )
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
