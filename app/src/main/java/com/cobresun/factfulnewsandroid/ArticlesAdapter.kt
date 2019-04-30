package com.cobresun.factfulnewsandroid

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
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

        // TODO: replace default variable
        val score = ((-1..1).random())
        val stringTemp: String      // replace stringTemp with article.sentiment

        stringTemp = when {
            score < 0 -> "Negative"
            score > 0 -> "Positive"
            else -> "Neutral"
        }

        holder.sentiment.text = stringTemp //article.sentiment
        holder.sentiment.setBackgroundColor(getSentimentColor(stringTemp))
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
        val sentiment: TextView = itemView.sentiment
    }
}

fun <T: RecyclerView.ViewHolder> T.onClick(event: (view: View, position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(it, adapterPosition, itemViewType)
    }
    return this
}