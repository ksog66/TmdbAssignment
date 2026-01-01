package com.kklabs.themoviedb.domain.usecase

import com.kklabs.themoviedb.domain.model.Resource
import com.kklabs.themoviedb.domain.model.HomeData
import com.kklabs.themoviedb.domain.repo.MoviesRepository

class GetHomeDataUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(): Resource<HomeData> {
        return repository.fetchAllCategories()
    }
}