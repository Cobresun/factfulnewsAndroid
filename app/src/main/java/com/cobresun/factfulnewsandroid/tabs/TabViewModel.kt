package com.cobresun.factfulnewsandroid.tabs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cobresun.factfulnewsandroid.models.Article
import com.cobresun.factfulnewsandroid.repositories.ArticlesRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class TabViewModel(
    private val readTime: Int,
    private val tabCategory: String,
    private val articlesRepository: ArticlesRepository
) : ViewModel() {
    private val _articles = MutableLiveData<List<Article>>().apply { value = emptyList() }
    val articles: LiveData<List<Article>> = _articles

    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.e("Caught $exception")
    }

    init {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val articles = articlesRepository
                .getArticles(tabCategory)
                .filter { it.timeToRead <= readTime }
            withContext(Dispatchers.Main) { _articles.value = articles }
        }
    }
}
