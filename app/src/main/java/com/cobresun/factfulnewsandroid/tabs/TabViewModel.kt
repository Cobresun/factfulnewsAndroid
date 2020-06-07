package com.cobresun.factfulnewsandroid.tabs

import androidx.lifecycle.*
import com.cobresun.factfulnewsandroid.models.Article
import com.cobresun.factfulnewsandroid.repositories.ArticlesRepository
import kotlinx.coroutines.launch

class TabViewModel(
    readTime: Int,
    tabCategory: String,
    articlesRepository: ArticlesRepository
) : ViewModel() {

    sealed class TabState {
        object LoadingState : TabState()
        data class ArticleData(val articles: List<Article>): TabState()
        data class ErrorState(val error: String): TabState()
    }

    private val _state: MutableLiveData<TabState> = MutableLiveData(TabState.LoadingState)
    val state: LiveData<TabState> = _state

    init {
        viewModelScope.launch {
            val articles = articlesRepository.getArticles(tabCategory).filter { it.timeToRead <= readTime }
            _state.postValue(TabState.ArticleData(articles))
        }
    }
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
