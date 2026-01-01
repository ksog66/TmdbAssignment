package com.kklabs.themoviedb.domain.usecase

import com.kklabs.themoviedb.domain.repo.MoviesRepository

class SearchMovieUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(query: String, page: Int) = repository.searchMovies(query,page)
}