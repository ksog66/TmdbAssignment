package com.kklabs.themoviedb.domain.usecase

import com.kklabs.themoviedb.domain.repo.MoviesRepository

class GetMovieDetailUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(id: Int) = repository.getMovieDetail(id)
}