package com.cobresun.factfulnewsandroid.tabs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import com.cobresun.factfulnewsandroid.R
import com.cobresun.factfulnewsandroid.databinding.ArticleRowBinding

class ArticlesAdapter(
    private val context: Context,
    private val clickListener: (Article) -> Unit,
    private val shareClickListener: (Article) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    private var articles: List<Article> = emptyList()

    class ArticleViewHolder(val binding: ArticleRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.binding.root.setOnClickListener { clickListener(articles[position]) }
        holder.binding.share.setOnClickListener { shareClickListener(articles[position]) }
        holder.binding.title.text = articles[position].title
        holder.binding.snippet.text = articles[position].snippet
        holder.binding.timeToRead.text = context.getString(R.string.time_to_read_format, articles[position].timeToRead)
        holder.binding.photo.load(articles[position].urlToImage) {
            scale(Scale.FIT)
        }
    }

    override fun getItemCount(): Int = articles.size

    fun setData(newArticles: List<Article>) {
        val diffUtil = ArticleDiffUtil(articles, newArticles)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        articles = newArticles
        diffResult.dispatchUpdatesTo(this)
    }
}
