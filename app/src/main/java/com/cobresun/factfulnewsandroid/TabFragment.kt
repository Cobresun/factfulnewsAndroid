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
import com.cobresun.factfulnewsandroid.repositories.SharedPrefsUserDataRepository
import kotlinx.android.synthetic.main.tab_fragment.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class TabFragment : Fragment(), CoroutineScope {

    companion object {
        private const val TAB_CATEGORY = "tab_category"

        fun newInstance(category: String) = TabFragment().apply {
            arguments = bundleOf(
                TAB_CATEGORY to category
            )
        }
    }

    private val tabCategory: String by lazy { arguments?.getString(TAB_CATEGORY) ?: "" }
    private val readTime: Int by lazy {
        SharedPrefsUserDataRepository(requireContext()).readUserReadTime()
    }

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
                val fetchResponse: FetchResponse = apiService.fetchArticles(tabCategory)
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
