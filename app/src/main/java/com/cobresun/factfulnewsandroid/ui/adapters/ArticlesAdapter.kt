package com.cobresun.factfulnewsandroid.ui.adapters

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cobresun.factfulnewsandroid.backend.models.Article
import com.cobresun.factfulnewsandroid.R
import kotlinx.android.synthetic.main.article_row.view.*

class ArticlesAdapter(private val context: Context, private val articles: List<Article>,
                      private val itemClickListener: (View, Int, Int) -> Unit) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

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
        holder.timeToRead.text = context.getString(R.string.ttrFormat, article.timeToRead)
        holder.share.setOnClickListener {
            // TODO: Make this use the newer share menu in Android
            //       instead of having it fall back to the old share
            //       implementation.
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, article.title + "\n\n" + article.url)
                type = "text/plain"
            }
            context.startActivity(shareIntent)
        }

        Glide.with(context).load(articles[position].urlToImage).override(holder.photo.width).into(holder.photo)
    }

    private fun getSentimentColor(sentiment: String): Int{
        return when (sentiment) {
            "Negative" -> Color.parseColor("#ffaaaa")
            "Positive" -> Color.parseColor("#aaffaa")
            else -> Color.parseColor("#aaaaff")
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.title
        val snippet: TextView = itemView.snippet
        val photo: ImageView = itemView.photo
        val timeToRead: TextView = itemView.timeToRead
        val share: ImageView = itemView.share
    }
}

fun <T: RecyclerView.ViewHolder> T.onClick(event: (view: View, position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(it, adapterPosition, itemViewType)
    }
    return this
}
