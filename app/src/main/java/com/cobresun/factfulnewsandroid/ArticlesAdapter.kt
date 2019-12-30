package com.cobresun.factfulnewsandroid

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cobresun.factfulnewsandroid.backend.models.Article
import kotlinx.android.synthetic.main.article_row.view.*

class ArticlesAdapter(
    private val context: Context,
    private val articles: List<Article>,
    private val itemClickListener: (View, Int, Int) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_row, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.onClick(itemClickListener)
        return viewHolder
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.snippet.text = article.snippet
        holder.timeToRead.text = context.getString(R.string.time_to_read_format, article.timeToRead)
        holder.share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, article.title + "\n\n" + article.url)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }

        Glide.with(context).load(articles[position].urlToImage).override(holder.photo.width)
            .into(holder.photo)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title
        val snippet: TextView = itemView.snippet
        val photo: ImageView = itemView.photo
        val timeToRead: TextView = itemView.timeToRead
        val share: ImageView = itemView.share
    }
}

fun <T : RecyclerView.ViewHolder> T.onClick(event: (view: View, position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(it, adapterPosition, itemViewType)
    }
    return this
}