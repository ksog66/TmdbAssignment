package com.kklabs.themoviedb.domain


import com.kklabs.themoviedb.data.models.MovieDetailDTO
import com.kklabs.themoviedb.data.models.MovieResponseDTO

interface NetworkDataSource {
    suspend fun getNowPlaying(page: Int = 1): Result<MovieResponseDTO>

    suspend fun getPopular(page: Int = 1): Result<MovieResponseDTO>

    suspend fun getTopRated(page: Int = 1): Result<MovieResponseDTO>

    suspend fun getUpcoming(page: Int = 1): Result<MovieResponseDTO>

    suspend fun getMovieDetail(id: Int): Result<MovieDetailDTO>
}