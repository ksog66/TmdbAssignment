package com.kklabs.themoviedb.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kklabs.themoviedb.domain.model.Movie
import com.kklabs.themoviedb.domain.model.Resource
import com.kklabs.themoviedb.domain.model.HomeData
import com.kklabs.themoviedb.domain.usecase.GetHomeDataUseCase
import com.kklabs.themoviedb.domain.usecase.SearchMovieUseCase
import com.kklabs.themoviedb.utils.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<HomeData>>(UiState.Loading)
    val uiState: StateFlow<UiState<HomeData>> = _uiState.asStateFlow()

    private val _searchState = MutableStateFlow(SearchUiState())
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchHomeData()
    }

    fun refresh() {
        fetchHomeData()
    }

    fun fetchHomeData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getHomeDataUseCase()

            when (result) {
                is Resource.Success -> {
                    _uiState.value = UiState.Success(
                        data = result.data,
                    )
                }
                is Resource.Error -> {
                    if (result.cachedData != null) {
                        _uiState.value = UiState.Success(
                            data = result.cachedData,
                            fromCache = true,
                        )
                    } else {
                        _uiState.value = UiState.Error(
                            message = result.message
                        )
                    }
                }
            }
        }
    }

    fun openSearch() {
        _searchState.value = _searchState.value.copy(isActive = true)
    }

    fun closeSearch() {
        _searchState.value = SearchUiState(isActive = false)
        searchJob?.cancel()
    }

    fun clearSearchText() {
        _searchState.value = _searchState.value.copy(query = "", results = emptyList())
    }

    fun onSearchQueryChange(query: String) {
        _searchState.value = _searchState.value.copy(query = query)

        searchJob?.cancel()
        if (query.isBlank()) {
            _searchState.value = _searchState.value.copy(results = emptyList())
            return
        }

        searchJob = viewModelScope.launch {
            delay(500)
            performSearch(query, page = 1, isNewSearch = true)
        }
    }

    fun loadNextSearchPage() {
        val state = _searchState.value
        if (!state.isLoading && state.currentPage < state.totalPages && state.query.isNotEmpty()) {
            viewModelScope.launch {
                performSearch(state.query, state.currentPage + 1, isNewSearch = false)
            }
        }
    }

    private suspend fun performSearch(query: String, page: Int, isNewSearch: Boolean) {
        _searchState.value = _searchState.value.copy(isLoading = true)
        searchMovieUseCase(query, page).collect { result ->
            result.onSuccess { moviePage ->
                _searchState.value = _searchState.value.copy(
                    isLoading = false,
                    results = if (isNewSearch) moviePage.movies else _searchState.value.results + moviePage.movies,
                    currentPage = moviePage.currentPage,
                    totalPages = moviePage.totalPages,
                    error = if (moviePage.movies.isEmpty() && isNewSearch) "No results found" else null
                )
            }.onFailure { error ->
                _searchState.value = _searchState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Search failed"
                )
            }
        }
    }
}


data class SearchUiState(
    val isActive: Boolean = false,
    val query: String = "",
    val results: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val totalPages: Int = 1
)
