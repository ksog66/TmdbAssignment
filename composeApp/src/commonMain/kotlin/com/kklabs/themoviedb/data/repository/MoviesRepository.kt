package com.kklabs.themoviedb.data.repository

import androidx.paging.ExperimentalPagingApi
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.map
import app.cash.sqldelight.paging3.QueryPagingSource
import com.kklabs.themoviedb.data.mapper.toDomain
import com.kklabs.themoviedb.data.paging.MovieRemoteMediator
import com.kklabs.themoviedb.database.MovieDatabase
import com.kklabs.themoviedb.domain.NetworkDataSource
import com.kklabs.themoviedb.domain.model.Movie
import com.kklabs.themoviedb.domain.model.MovieDetail
import com.kklabs.themoviedb.domain.model.MoviePage
import com.kklabs.themoviedb.domain.model.Resource
import com.kklabs.themoviedb.domain.model.HomeData
import com.kklabs.themoviedb.domain.repo.MoviesRepository
import com.kklabs.themoviedb.domain.model.MovieListType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val db: MovieDatabase
) : MoviesRepository {

    override suspend fun fetchAllCategories(): Resource<HomeData> {
        return try {
            withContext(Dispatchers.IO) {
                coroutineScope {
                    val nowPlayingDef = async { networkDataSource.getNowPlaying(1) }
                    val popularDef = async { networkDataSource.getPopular(1) }
                    val topRatedDef = async { networkDataSource.getTopRated(1) }
                    val upcomingDef = async { networkDataSource.getUpcoming(1) }

                    val nowPlaying = nowPlayingDef.await().getOrThrow().toDomain().movies.take(10)
                    val popular = popularDef.await().getOrThrow().toDomain().movies.take(10)
                    val topRated = topRatedDef.await().getOrThrow().toDomain().movies.take(10)
                    val upcoming = upcomingDef.await().getOrThrow().toDomain().movies.take(10)

                    db.transaction {
                        saveCategoryToDb("now_playing", nowPlaying)
                        saveCategoryToDb("popular", popular)
                        saveCategoryToDb("top_rated", topRated)
                        saveCategoryToDb("upcoming", upcoming)
                    }

                    Resource.Success(
                        HomeData(nowPlaying, popular, topRated, upcoming)
                    )
                }
            }
        } catch (e: Exception) {
            val cachedData = fetchHomeDataFromDb()

            if (cachedData.isEmpty()) {
                Resource.Error(
                    message = e.message ?: "Something went wrong",
                    cachedData = null
                )
            } else {
                Resource.Error(
                    message = "No Internet Connection. Showing cached data.",
                    cachedData = cachedData
                )
            }
        }
    }

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

    @OptIn(ExperimentalPagingApi::class, app.cash.paging.ExperimentalPagingApi::class)
    override fun getPagedMovies(listType: MovieListType): Flow<PagingData<Movie>> {
        val typeName = listType.name

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20
            ),
            remoteMediator = MovieRemoteMediator(
                listType = typeName,
                database = db,
                apiService = { page ->
                    when (listType) {
                        MovieListType.POPULAR -> fetchFromFlow { getPopular(page) }
                        MovieListType.TOP_RATED -> fetchFromFlow { getTopRated(page) }
                        MovieListType.UPCOMING -> fetchFromFlow { getUpcoming(page) }
                        else -> {
                            Result.failure(IllegalStateException("Unexpected list type: $typeName"))
                        }
                    }
                }
            ),
            pagingSourceFactory = {
                QueryPagingSource(
                    countQuery = db.movieQueries.countMovies(typeName),
                    transacter = db.movieQueries,
                    context = Dispatchers.IO,
                    queryProvider = { limit, offset ->
                        db.movieQueries.getPagedMovies(typeName, limit, offset)
                    }
                )
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map { entity -> entity.toDomain() }
            }
    }

    override suspend fun searchMovies(
        query: String,
        page: Int
    ): Flow<Result<MoviePage>> = flow {
        val networkResult = networkDataSource.searchMovies(query, page)
        networkResult.onSuccess {
            emit(Result.success(it.toDomain()))
        }.onFailure { error ->
            emit(Result.failure(error))
        }
    }

    private fun saveCategoryToDb(type: String, movies: List<Movie>) {
        db.movieQueries.clearMovies(type)
        movies.forEachIndexed { index, movie ->
            db.movieQueries.insertMovie(
                id = movie.id.toLong(),
                title = movie.title,
                posterPath = movie.posterPath,
                backdropPath = movie.backdropPath,
                voteAverage = movie.voteAverage,
                overview = movie.overview,
                releaseDate = movie.releaseDate,
                adult = movie.adult,
                listType = type,
                page = 1,
                indexInResponse = index.toLong()
            )
        }
    }

    private fun fetchHomeDataFromDb(): HomeData {
        return HomeData(
            nowPlayingMovies = getPreviewList("now_playing"),
            popularMovies = getPreviewList("popular"),
            topRatedMovies = getPreviewList("top_rated"),
            upcomingMovies = getPreviewList("upcoming")
        )
    }

    private fun getPreviewList(type: String): List<Movie> {
        return db.movieQueries
            .getPreviewMovies(type)
            .executeAsList()
            .map { it.toDomain() }
    }
    private suspend fun fetchFromFlow(
        call: suspend () -> Flow<Result<MoviePage>>
    ): Result<MoviePage> {

        return try {
            call()
                .catch { emit(Result.failure(it)) }
                .first()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}