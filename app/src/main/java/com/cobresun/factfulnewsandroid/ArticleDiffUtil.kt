package com.cobresun.factfulnewsandroid

import androidx.recyclerview.widget.DiffUtil
import com.cobresun.factfulnewsandroid.models.Article

class ArticleDiffUtil(
    private val oldList: List<Article>,
    private val newList: List<Article>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].title != newList[newItemPosition].title -> false
            oldList[oldItemPosition].urlToImage != newList[newItemPosition].urlToImage -> false
            oldList[oldItemPosition].snippet != newList[newItemPosition].snippet -> false
            oldList[oldItemPosition].url != newList[newItemPosition].url -> false
            oldList[oldItemPosition].timeToRead != newList[newItemPosition].timeToRead -> false
            oldList[oldItemPosition].sentiment != newList[newItemPosition].sentiment -> false
            else -> true
        }
    }
}
