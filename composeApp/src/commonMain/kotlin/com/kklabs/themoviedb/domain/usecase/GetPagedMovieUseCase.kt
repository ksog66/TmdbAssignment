package com.kklabs.themoviedb.domain.usecase

import com.kklabs.themoviedb.domain.repo.MoviesRepository
import com.kklabs.themoviedb.domain.model.MovieListType

class GetPagedMoviesUseCase(
    private val repository: MoviesRepository
) {
    operator fun invoke(listType: MovieListType) = repository.getPagedMovies(listType)
}