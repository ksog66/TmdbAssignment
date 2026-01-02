package com.kklabs.themoviedb.domain.repo

import app.cash.paging.PagingData
import com.kklabs.themoviedb.domain.model.Movie
import com.kklabs.themoviedb.domain.model.MovieDetail
import com.kklabs.themoviedb.domain.model.MoviePage
import com.kklabs.themoviedb.domain.model.Resource
import com.kklabs.themoviedb.domain.model.HomeData
import com.kklabs.themoviedb.domain.model.MovieListType
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun fetchAllCategories(): Resource<HomeData>

    suspend fun getNowPlaying(page: Int): Flow<Result<MoviePage>>

    suspend fun getPopular(page: Int): Flow<Result<MoviePage>>

    suspend fun getTopRated(page: Int): Flow<Result<MoviePage>>

    suspend fun getUpcoming(page: Int): Flow<Result<MoviePage>>

    suspend fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>>

    fun getPagedMovies(listType: MovieListType): Flow<PagingData<Movie>>

    suspend fun searchMovies(query: String, page: Int): Flow<Result<MoviePage>>
}