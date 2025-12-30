package com.kklabs.themoviedb.domain.usecase

import com.kklabs.themoviedb.domain.repo.MoviesRepository

class GetPopularMovieUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(page: Int) = repository.getPopular(page)
}