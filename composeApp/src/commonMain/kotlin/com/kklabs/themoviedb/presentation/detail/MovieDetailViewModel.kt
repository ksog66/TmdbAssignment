package com.kklabs.themoviedb.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kklabs.themoviedb.domain.model.MovieDetail
import com.kklabs.themoviedb.domain.model.Resource
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

            val result = getMovieDetailUseCase(movieId).extractMovieDetail()

            _uiState.value = when (result) {
                is Resource.Success -> {
                    UiState.Success(result.data)
                }
                is Resource.Error -> {
                    if (result.cachedData != null) {
                        UiState.Success(
                            data = result.cachedData,
                            fromCache = true
                        )
                    } else {
                        UiState.Error(message = result.message)
                    }
                }
            }
        }
    }

    fun refresh(movieId: Int) {
        fetchMovieDetail(movieId)
    }

    private suspend fun Flow<Resource<MovieDetail>>.extractMovieDetail(): Resource<MovieDetail> {
        return this
            .catch { emit(Resource.Error(it.message ?: "Unknow Error Occurred")) }
            .first()
    }
}


