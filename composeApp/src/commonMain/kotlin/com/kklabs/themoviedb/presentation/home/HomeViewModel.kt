package com.kklabs.themoviedb.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kklabs.themoviedb.domain.model.Movie
import com.kklabs.themoviedb.domain.model.MoviePage
import com.kklabs.themoviedb.domain.model.vo.HomeData
import com.kklabs.themoviedb.domain.usecase.GetNowPlayingMovieUseCase
import com.kklabs.themoviedb.domain.usecase.GetPopularMovieUseCase
import com.kklabs.themoviedb.domain.usecase.GetTopRatedMovieUseCase
import com.kklabs.themoviedb.domain.usecase.GetUpcomingMovieUseCase
import com.kklabs.themoviedb.utils.Logger
import com.kklabs.themoviedb.utils.UiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getNowPlayingMovieUseCase: GetNowPlayingMovieUseCase,
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getTopRatedMovieUseCase: GetTopRatedMovieUseCase,
    private val getUpcomingMovieUseCase: GetUpcomingMovieUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<HomeData>>(UiState.Loading)
    val uiState: StateFlow<UiState<HomeData>> = _uiState.asStateFlow()

    init {
        fetchHomeData()
    }

    fun refresh() {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val nowPlayingDeferred = async { getNowPlayingMovieUseCase(1).extractMovies() }
                val popularDeferred = async { getPopularMovieUseCase(1).extractMovies() }
                val topRatedDeferred = async { getTopRatedMovieUseCase(1).extractMovies() }
                val upcomingDeferred = async { getUpcomingMovieUseCase(1).extractMovies() }

                val homeData = HomeData(
                    nowPlayingMovies = nowPlayingDeferred.await(),
                    popularMovies = popularDeferred.await(),
                    topRatedMovies = topRatedDeferred.await(),
                    upcomingMovies = upcomingDeferred.await()
                )

                _uiState.value = UiState.Success(homeData)

            } catch (e: Exception) {
                _uiState.value = UiState.Error(message = e.message)
            }
        }
    }

    private suspend fun Flow<Result<MoviePage>>.extractMovies(): List<Movie> {
        return this
            .catch { emit(Result.failure(it)) }
            .first()
            .getOrNull()
            ?.movies
            ?.take(10)
            ?: emptyList()
    }


}

