package com.cobresun.factfulnewsandroid.tabs

import androidx.lifecycle.*
import com.cobresun.factfulnewsandroid.repositories.ArticlesRepository
import com.cobresun.factfulnewsandroid.repositories.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabViewModel @Inject constructor(
    userPreferences: UserPreferences,
    savedStateHandle: SavedStateHandle,
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
        val categoryString : String = savedStateHandle["category"]!!
        val category: Category = Category.values().first { it.value == categoryString }

        viewModelScope.launch(handler) {
            val articles = articlesRepository.getArticles(category).filter {
                it.timeToRead ?: 0 <= userPreferences.userReadTime()
            }
            _state.postValue(TabState.ArticleData(articles))
        }
    }
}
