package com.kklabs.themoviedb.data.repository

import com.kklabs.themoviedb.data.mapper.toDomain
import com.kklabs.themoviedb.data.models.MovieDetailDTO
import com.kklabs.themoviedb.data.models.MovieResponseDTO
import com.kklabs.themoviedb.domain.NetworkDataSource
import com.kklabs.themoviedb.domain.model.MovieDetail
import com.kklabs.themoviedb.domain.model.MoviePage
import com.kklabs.themoviedb.domain.repo.MoviesRepository

class MoviesRepositoryImpl (
    private val networkDataSource: NetworkDataSource
) : MoviesRepository {
    override suspend fun getNowPlaying(page: Int): Result<MoviePage> {
        return networkDataSource.getNowPlaying(page).map(MovieResponseDTO::toDomain)
    }

    override suspend fun getPopular(page: Int): Result<MoviePage> {
        return networkDataSource.getPopular(page).map(MovieResponseDTO::toDomain)
    }

    override suspend fun getTopRated(page: Int): Result<MoviePage> {
        return networkDataSource.getTopRated(page).map(MovieResponseDTO::toDomain)
    }

    override suspend fun getUpcoming(page: Int): Result<MoviePage> {
        return networkDataSource.getUpcoming(page).map(MovieResponseDTO::toDomain)
    }

    override suspend fun getMovieDetail(id: Int): Result<MovieDetail> {
        return networkDataSource.getMovieDetail(id).map(MovieDetailDTO::toDomain)
    }
}