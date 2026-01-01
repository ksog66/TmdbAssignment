package com.kklabs.themoviedb.domain


import com.kklabs.themoviedb.data.models.MovieDetailDTO
import com.kklabs.themoviedb.data.models.MovieResponseDTO

interface NetworkDataSource {
    suspend fun getNowPlaying(page: Int): Result<MovieResponseDTO>

    suspend fun getPopular(page: Int): Result<MovieResponseDTO>

    suspend fun getTopRated(page: Int): Result<MovieResponseDTO>

    suspend fun getUpcoming(page: Int): Result<MovieResponseDTO>

    suspend fun getMovieDetail(id: Int): Result<MovieDetailDTO>

    suspend fun searchMovies(query: String, page: Int): Result<MovieResponseDTO>
}