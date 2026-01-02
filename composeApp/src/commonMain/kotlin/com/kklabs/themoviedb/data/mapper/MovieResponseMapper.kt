package com.kklabs.themoviedb.data.mapper

import com.kklabs.themoviedb.data.models.MovieDTO
import com.kklabs.themoviedb.data.models.MovieDetailDTO
import com.kklabs.themoviedb.data.models.GenreDTO
import com.kklabs.themoviedb.data.models.MovieResponseDTO
import com.kklabs.themoviedb.database.MovieDetailEntity
import com.kklabs.themoviedb.database.MovieEntity
import com.kklabs.themoviedb.domain.model.Movie
import com.kklabs.themoviedb.domain.model.MovieDetail
import com.kklabs.themoviedb.domain.model.Genre
import com.kklabs.themoviedb.domain.model.MoviePage
import kotlinx.serialization.json.Json


fun MovieResponseDTO.toMovie(): MoviePage {
    return MoviePage(
        movies = this.results.map {
            it.toDomain()
        },
        currentPage = this.page,
        totalPages = this.totalPages
    )
}

fun MovieDTO.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        overview = this.overview,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        adult = this.adult,
    )
}

fun MovieDetailDTO.toMovie(): MovieDetail {
    return MovieDetail(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        genres = this.genres.map { it.toMovie() },
        adult = this.adult,
        budget = this.budget,
        revenue = this.revenue,
        runtime = this.runtime,
        status = this.status,
        tagline = this.tagline,
    )
}

fun GenreDTO.toMovie(): Genre {
    return Genre(
        id = this.id,
        name = this.name,
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id.toInt(),
        title = this.title,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage,
        overview = this.overview,
        releaseDate = this.releaseDate,
        backdropPath = this.backdropPath,
        adult = this.adult
    )
}

fun MovieDetailEntity.toDomain(): MovieDetail {
    val genresList: List<Genre> = try {
        Json.decodeFromString(this.genres ?: "[]")
    } catch (e: Exception) {
        emptyList()
    }

    return MovieDetail(
        id = this.id.toInt(),
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate ?: "",
        voteAverage = this.voteAverage,
        genres = genresList,
        runtime = this.runtime?.toInt(),
        status = this.status ?: "",
        tagline = this.tagline,
        adult = this.adult,
        budget = this.budget,
        revenue = this.revenue
    )
}


