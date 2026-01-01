package com.kklabs.themoviedb.data.paging

import app.cash.paging.ExperimentalPagingApi
import app.cash.paging.LoadType
import app.cash.paging.PagingState
import app.cash.paging.RemoteMediator
import app.cash.paging.RemoteMediatorMediatorResult
import app.cash.paging.RemoteMediatorMediatorResultError
import app.cash.paging.RemoteMediatorMediatorResultSuccess
import com.kklabs.themoviedb.database.MovieDatabase
import com.kklabs.themoviedb.database.MovieEntity
import com.kklabs.themoviedb.domain.model.MoviePage

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val listType: String,
    private val database: MovieDatabase,
    private val apiService: suspend (page: Int) -> Result<MoviePage>
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): RemoteMediatorMediatorResult {

        return try {
            val loadPage = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return RemoteMediatorMediatorResultSuccess(endOfPaginationReached = true) as RemoteMediatorMediatorResult
                }
                LoadType.APPEND -> {
                    val lastPage = database.movieQueries
                        .getLastPage(listType)
                        .executeAsOneOrNull()
                        ?.max_page?.toInt()
                        ?: 1
                    lastPage + 1
                }
                else -> {
                    return RemoteMediatorMediatorResultError(
                        throwable = IllegalStateException("Unexpected LoadType: $loadType")
                    ) as RemoteMediatorMediatorResult
                }
            }

            val responseResult = apiService(loadPage)
            val response = responseResult.getOrNull()

            if (response == null) {
                return RemoteMediatorMediatorResultError(
                    throwable = Exception("Network Error or Empty Response")
                ) as RemoteMediatorMediatorResult
            }

            database.transaction {
                if (loadType == LoadType.REFRESH) {
                    database.movieQueries.clearMovies(listType)
                }

                response.movies.forEachIndexed { index, movie ->
                    database.movieQueries.insertMovie(
                        id = movie.id.toLong(),
                        title = movie.title,
                        posterPath = movie.posterPath,
                        backdropPath = movie.backdropPath,
                        voteAverage = movie.voteAverage,
                        overview = movie.overview,
                        releaseDate = movie.releaseDate,
                        adult = movie.adult,
                        listType = listType,
                        page = response.currentPage.toLong(),
                        indexInResponse = index.toLong()
                    )
                }
            }

            RemoteMediatorMediatorResultSuccess(
                endOfPaginationReached = response.currentPage >= response.totalPages
            ) as RemoteMediatorMediatorResult

        } catch (e: Throwable) {
            RemoteMediatorMediatorResultError(throwable = e) as RemoteMediatorMediatorResult
        }
    }
}