package com.cobresun.factfulnewsandroid.tabs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobresun.factfulnewsandroid.ArticlesAdapter
import com.cobresun.factfulnewsandroid.R
import com.cobresun.factfulnewsandroid.models.Article
import com.cobresun.factfulnewsandroid.repositories.ArticlesRepository
import com.cobresun.factfulnewsandroid.repositories.SharedPrefsUserDataRepository
import kotlinx.android.synthetic.main.tab_fragment.*

class TabFragment : Fragment() {

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
        SharedPrefsUserDataRepository(
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        ).readUserReadTime()
    }

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

    private val articlesAdapter by lazy {
        ArticlesAdapter(
            requireContext(),
            emptyList(),
            articleClickListener,
            articleShareClickListener
        )
    }

    private val viewModel: TabViewModel by viewModels {
        TabViewModelFactory(readTime, tabCategory, ArticlesRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when(it) {
                is TabViewModel.TabState.LoadingState -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                is TabViewModel.TabState.ArticleData -> {
                    articlesAdapter.setData(it.articles)
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
                is TabViewModel.TabState.ErrorState -> {
                    Log.e("Error", it.error)
                    Toast.makeText(requireContext(), getString(R.string.failed_fetch_alert), Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
