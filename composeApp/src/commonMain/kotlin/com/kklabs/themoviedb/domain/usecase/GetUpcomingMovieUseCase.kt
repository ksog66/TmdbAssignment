package com.kklabs.themoviedb.domain.usecase

import com.kklabs.themoviedb.domain.repo.MoviesRepository

class GetUpcomingMovieUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(page: Int) = repository.getUpcoming(page)
}