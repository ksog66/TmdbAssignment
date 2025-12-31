package com.kklabs.themoviedb.data.repository

import com.kklabs.themoviedb.data.mapper.toDomain
import com.kklabs.themoviedb.domain.NetworkDataSource
import com.kklabs.themoviedb.domain.model.MovieDetail
import com.kklabs.themoviedb.domain.model.MoviePage
import com.kklabs.themoviedb.domain.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl (
    private val networkDataSource: NetworkDataSource
) : MoviesRepository {
    override suspend fun getNowPlaying(page: Int): Flow<Result<MoviePage>> = flow {
        val networkResult = networkDataSource.getNowPlaying(page)
        networkResult.onSuccess {
            emit(Result.success(it.toDomain()))
        }.onFailure { error ->
            emit(Result.failure(error))
        }
    }

    override suspend fun getPopular(page: Int): Flow<Result<MoviePage>> = flow {
        val networkResult = networkDataSource.getPopular(page)
        networkResult.onSuccess {
            emit(Result.success(it.toDomain()))
        }.onFailure { error ->
            emit(Result.failure(error))
        }
    }

    override suspend fun getTopRated(page: Int): Flow<Result<MoviePage>> = flow {
        val networkResult = networkDataSource.getTopRated(page)
        networkResult.onSuccess {
            emit(Result.success(it.toDomain()))
        }.onFailure { error ->
            emit(Result.failure(error))
        }
    }

    override suspend fun getUpcoming(page: Int): Flow<Result<MoviePage>> = flow {
        val networkResult = networkDataSource.getUpcoming(page)
        networkResult.onSuccess {
            emit(Result.success(it.toDomain()))
        }.onFailure { error ->
            emit(Result.failure(error))
        }
    }

    override suspend fun getMovieDetail(id: Int): Flow<Result<MovieDetail>> = flow {
        val networkResult = networkDataSource.getMovieDetail(id)
        networkResult.onSuccess {
            emit(Result.success(it.toDomain()))
        }.onFailure { error ->
            emit(Result.failure(error))
        }
    }
}