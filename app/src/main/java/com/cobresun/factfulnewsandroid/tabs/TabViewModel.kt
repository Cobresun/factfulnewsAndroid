package com.cobresun.factfulnewsandroid.tabs

import androidx.lifecycle.*
import com.cobresun.factfulnewsandroid.models.Article
import com.cobresun.factfulnewsandroid.repositories.ArticlesRepository
import com.cobresun.factfulnewsandroid.repositories.UserPreferences
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class TabViewModel(
    userPreferences: UserPreferences,
    tabCategory: String,
    articlesRepository: ArticlesRepository
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        _state.postValue(TabState.ErrorState(exception.toString()))
    }

    sealed class TabState {
        object LoadingState : TabState()
        data class ArticleData(val articles: List<Article>): TabState()
        data class ErrorState(val error: String): TabState()
    }

    private val _state: MutableLiveData<TabState> = MutableLiveData(TabState.LoadingState)
    val state: LiveData<TabState> = _state

    init {
        viewModelScope.launch(handler) {
            val articles = articlesRepository.getArticles(tabCategory).filter {
                it.timeToRead <= userPreferences.userReadTime()
            }
            _state.postValue(TabState.ArticleData(articles))
        }
    }
}

@Suppress("UNCHECKED_CAST")
class TabViewModelFactory(
    private val userPreferences: UserPreferences,
    private val tabCategory: String,
    private val articlesRepository: ArticlesRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TabViewModel(userPreferences, tabCategory, articlesRepository) as T
    }
}
