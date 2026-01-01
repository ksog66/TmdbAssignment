package com.kklabs.themoviedb.domain.usecase

import app.cash.paging.PagingData
import com.kklabs.themoviedb.domain.model.Movie
import com.kklabs.themoviedb.domain.repo.MoviesRepository
import com.kklabs.themoviedb.domain.model.MovieListType
import kotlinx.coroutines.flow.Flow

class GetPagedMoviesUseCase(
    private val repository: MoviesRepository
) {
    operator fun invoke(listType: MovieListType): Flow<PagingData<Movie>> {
        return repository.getPagedMovies(listType)
    }
}