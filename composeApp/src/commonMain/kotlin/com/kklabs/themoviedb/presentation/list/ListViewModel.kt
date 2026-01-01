package com.kklabs.themoviedb.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.kklabs.themoviedb.domain.model.Movie
import com.kklabs.themoviedb.domain.model.MovieListType
import com.kklabs.themoviedb.domain.usecase.GetPagedMoviesUseCase
import kotlinx.coroutines.flow.Flow

class ListViewModel(
    private val getPagedMoviesUseCase: GetPagedMoviesUseCase
) : ViewModel() {

    fun getPagedMovies(listType: MovieListType): Flow<PagingData<Movie>> {
        return getPagedMoviesUseCase(listType)
            .cachedIn(viewModelScope)
    }
}


