package com.kklabs.themoviedb.data.remote

import com.kklabs.themoviedb.data.models.MovieDetailDTO
import com.kklabs.themoviedb.data.models.MovieResponseDTO
import com.kklabs.themoviedb.domain.NetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class NetworkDataSourceImpl (
    private val httpClient: HttpClient
) : NetworkDataSource {
    override suspend fun getNowPlaying(page: Int): Result<MovieResponseDTO> {
        return try {
            val response = httpClient.get("/movie/now_playing?page=$page").body<MovieResponseDTO>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPopular(page: Int): Result<MovieResponseDTO> {
        return try {
            val response = httpClient.get("/movie/popular?page=$page").body<MovieResponseDTO>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTopRated(page: Int): Result<MovieResponseDTO> {
        return try {
            val response = httpClient.get("/movie/top_rated?page=$page").body<MovieResponseDTO>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUpcoming(page: Int): Result<MovieResponseDTO> {
        return try {
            val response = httpClient.get("/movie/upcoming?page=$page").body<MovieResponseDTO>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetail(id: Int): Result<MovieDetailDTO> {
        return try {
            val response = httpClient.get("/movie/$id").body<MovieDetailDTO>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchMovies(
        query: String,
        page: Int
    ): Result<MovieResponseDTO> {
        return try {
            val response = httpClient.get("/search/movie?query=$query&page=$page").body<MovieResponseDTO>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}