package com.example.meli.home.viewmodel

import com.example.meli.core.common.di.ScopeMainNoInmediate
import com.example.meli.core.ui.viewmodel.StateViewModel
import com.example.meli.core.ui.viewmodel.UIContract
import com.example.meli.home.domain.entity.QueryResult
import com.example.meli.home.domain.usecase.GetSuggestedQueriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface HomeContract {
    data class State(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val data: List<String> = emptyList(),
        val suggestedQueries: QueryResult? = null
    ) : UIContract.State

    sealed class Intent: UIContract.Intent {
        data class Search(val query: String): Intent()
        data class RequestSuggestedQueries(val query: String): Intent()
        data class ClickBackFromSearch(val query: String): Intent()
        data object SwapSearchingState: Intent()
        data object ClearSearch: Intent()
    }

    sealed class Effect: UIContract.Effect {
        data class ShowToast(val message: String): Effect()
    }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSuggestedQueries: GetSuggestedQueriesUseCase,
    @ScopeMainNoInmediate private val coroutineScope: CoroutineScope
): StateViewModel<
        HomeContract.State,
        HomeContract.Effect,
        HomeContract.Intent
>(HomeContract.State()) {

    init {
        handleSuggestedQueries()
    }

    private val suggestedQueriesFlow =
        MutableSharedFlow<HomeContract.Intent.RequestSuggestedQueries>()

    override fun dispatch(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.Search -> onSearch(intent.query)
            is HomeContract.Intent.RequestSuggestedQueries -> {
                launch { suggestedQueriesFlow.emit(intent) }
            }
            is HomeContract.Intent.ClickBackFromSearch -> {}
            HomeContract.Intent.SwapSearchingState -> {}
            HomeContract.Intent.ClearSearch -> {}
        }
    }

    @OptIn(FlowPreview::class)
    private fun handleSuggestedQueries() {
        coroutineScope.launch {
            suggestedQueriesFlow
                .debounce(DEFAULT_DELAY_FOR_SEARCH)
                .collect { getSuggestedQueries(it.query) }
        }
    }

    private fun getSuggestedQueries(query: String) {
        launch {
            val result = getSuggestedQueries.invoke(
                siteId = DEFAULT_COUNTRY,
                query = query,
                limit = DEFAULT_LIMIT_FOR_SUGGESTED_QUERIES
            )
            updateState { copy(suggestedQueries = result) }
        }
    }

    private fun onSearch(query: String) {
        /*update { copy(querySearch = querySearch, isSearching = false) }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = getProducts.invoke(siteId = com.mercadolibre.android.ui.home.HomeViewModel.Companion.DEFAULT_COUNTRY, query = query)
                mutableState.update { copy(productResult = result) }
            }
        }*/
    }

    companion object {
        const val DEFAULT_DELAY_FOR_SEARCH = 300L
        const val DEFAULT_COUNTRY = "MCO"
        const val DEFAULT_LIMIT_FOR_SUGGESTED_QUERIES = 5
    }
}
