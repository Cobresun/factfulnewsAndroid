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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobresun.factfulnewsandroid.R
import com.cobresun.factfulnewsandroid.databinding.TabFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabFragment : Fragment() {

    private var _binding: TabFragmentBinding? = null
    private val binding get() = _binding!!

    private val articleClickListener: (Article) -> Unit = {
        CustomTabsIntent
            .Builder()
            .setShowTitle(true)
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

    private val viewModel: TabViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TabFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articlesAdapter = ArticlesAdapter(
            requireContext(),
            articleClickListener,
            articleShareClickListener
        )

        binding.recyclerView.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                is TabViewModel.TabState.LoadingState -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is TabViewModel.TabState.ArticleData -> {
                    articlesAdapter.setData(state.articles)
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                // TODO: Need to re-try if server was unable to respond first time
                is TabViewModel.TabState.ErrorState -> {
                    Log.e("Error", state.error)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.failed_fetch_alert),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
