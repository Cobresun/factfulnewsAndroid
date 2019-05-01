package com.cobresun.factfulnewsandroid.ui.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
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
        holder.sentiment.text = article.sentiment
        holder.sentiment.setBackgroundColor(getSentimentColor(article.sentiment))

        holder.articleMenu.setOnClickListener {
            // TODO: Perhaps we could turn this into a bottom sheet
            //       dialog at some point, instead of using a popup
            //       menu, but this works for right now.
            val menu = PopupMenu(context, holder.articleMenu)
            menu.inflate(R.menu.menu_article)
            menu.setOnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.action_share -> {
                        val shareIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, article.title + "\n\n" + article.url)
                            type = "text/plain"
                        }
                        context.startActivity(shareIntent)
                    }
                }
                true
            }
            menu.show()
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
        val sentiment: TextView = itemView.sentiment
        val articleMenu: ImageView = itemView.article_menu
    }
}

fun <T: RecyclerView.ViewHolder> T.onClick(event: (view: View, position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(it, adapterPosition, itemViewType)
    }
    return this
}
