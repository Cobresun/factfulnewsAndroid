package com.cobresun.factfulnewsandroid.tabs

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.cobresun.factfulnewsandroid.models.Article
import com.cobresun.factfulnewsandroid.repositories.ArticlesRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class TabViewModel(
    private val readTime: Int,
    private val tabCategory: String,
    private val articlesRepository: ArticlesRepository
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.e("Caught $exception")
    }

    private val _articles = liveData(Dispatchers.IO + handler) {
        val articles = articlesRepository
            .getArticles(tabCategory)
            .filter { it.timeToRead <= readTime }
        emit(articles)
    }
    val articles: LiveData<List<Article>> = _articles

}

@Suppress("UNCHECKED_CAST")
class TabViewModelFactory(
    private val readTime: Int,
    private val tabCategory: String,
    private val articlesRepository: ArticlesRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TabViewModel(readTime, tabCategory, articlesRepository) as T
    }
}
