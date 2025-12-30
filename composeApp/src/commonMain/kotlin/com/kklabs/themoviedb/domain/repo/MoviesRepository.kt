package com.kklabs.themoviedb.domain.repo

import com.kklabs.themoviedb.domain.model.MovieDetail
import com.kklabs.themoviedb.domain.model.MoviePage

interface MoviesRepository {

    suspend fun getNowPlaying(page: Int): Result<MoviePage>

    suspend fun getPopular(page: Int): Result<MoviePage>

    suspend fun getTopRated(page: Int): Result<MoviePage>

    suspend fun getUpcoming(page: Int): Result<MoviePage>

    suspend fun getMovieDetail(id: Int): Result<MovieDetail>
}