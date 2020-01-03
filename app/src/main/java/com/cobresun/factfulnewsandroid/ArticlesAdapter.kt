package com.cobresun.factfulnewsandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import com.cobresun.factfulnewsandroid.models.Article
import kotlinx.android.synthetic.main.article_row.view.*

class ArticlesAdapter(
    private val context: Context,
    private var articles: List<Article>,
    private val clickListener: (Article) -> Unit,
    private val shareClickListener: (Article) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_row, parent, false)
        return ViewHolder(view)
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title
        val snippet: TextView = itemView.snippet
        val photo: ImageView = itemView.photo
        val timeToRead: TextView = itemView.timeToRead
        val share: ImageView = itemView.share
    }
}
