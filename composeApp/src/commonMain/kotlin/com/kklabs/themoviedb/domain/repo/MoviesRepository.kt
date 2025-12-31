package com.kklabs.themoviedb.domain.repo

import com.kklabs.themoviedb.domain.model.MovieDetail
import com.kklabs.themoviedb.domain.model.MoviePage
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getNowPlaying(page: Int): Flow<Result<MoviePage>>

    suspend fun getPopular(page: Int): Flow<Result<MoviePage>>

    suspend fun getTopRated(page: Int): Flow<Result<MoviePage>>

    suspend fun getUpcoming(page: Int): Flow<Result<MoviePage>>

    suspend fun getMovieDetail(id: Int): Flow<Result<MovieDetail>>
}