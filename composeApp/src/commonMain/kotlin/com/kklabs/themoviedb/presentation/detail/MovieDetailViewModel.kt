package com.kklabs.themoviedb.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kklabs.themoviedb.domain.model.MovieDetail
import com.kklabs.themoviedb.domain.usecase.GetMovieDetailUseCase
import com.kklabs.themoviedb.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<MovieDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<MovieDetail>> = _uiState.asStateFlow()

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val movieDetail = getMovieDetailUseCase(movieId).extractMovieDetail()
                if (movieDetail != null) {
                    _uiState.value = UiState.Success(movieDetail)
                } else {
                    _uiState.value = UiState.Error(message = "Failed to load movie details")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(message = e.message ?: "Something went wrong")
            }
        }
    }

    fun refresh(movieId: Int) {
        fetchMovieDetail(movieId)
    }

    private suspend fun Flow<Result<MovieDetail>>.extractMovieDetail(): MovieDetail? {
        return this
            .catch { emit(Result.failure(it)) }
            .first()
            .getOrNull()
    }
}


