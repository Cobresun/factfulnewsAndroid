package com.cobresun.factfulnewsandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import com.cobresun.factfulnewsandroid.databinding.ArticleRowBinding
import com.cobresun.factfulnewsandroid.models.Article

class ArticlesAdapter(
    private val context: Context,
    private var articles: List<Article>,
    private val clickListener: (Article) -> Unit,
    private val shareClickListener: (Article) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArticleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { clickListener(articles[position]) }
        holder.share.setOnClickListener { shareClickListener(articles[position]) }
        holder.title.text = articles[position].title
        holder.snippet.text = articles[position].snippet
        holder.timeToRead.text = context.getString(R.string.time_to_read_format, articles[position].timeToRead)
        holder.photo.load(articles[position].urlToImage) {
            scale(Scale.FIT)
        }
    }

    fun setData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    class ViewHolder(binding: ArticleRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.title
        val snippet: TextView = binding.snippet
        val photo: ImageView = binding.photo
        val timeToRead: TextView = binding.timeToRead
        val share: ImageView = binding.share
    }
}
