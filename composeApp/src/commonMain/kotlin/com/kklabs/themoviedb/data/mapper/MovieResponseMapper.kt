package com.kklabs.themoviedb.data.mapper

import com.kklabs.themoviedb.data.models.MovieDTO
import com.kklabs.themoviedb.data.models.MovieDetailDTO
import com.kklabs.themoviedb.data.models.GenreDTO
import com.kklabs.themoviedb.data.models.MovieResponseDTO
import com.kklabs.themoviedb.domain.model.Movie
import com.kklabs.themoviedb.domain.model.MovieDetail
import com.kklabs.themoviedb.domain.model.Genre
import com.kklabs.themoviedb.domain.model.MoviePage


fun MovieResponseDTO.toDomain(): MoviePage {
    return MoviePage(
        movies = this.results.map {it.toDomain()},
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

fun MovieDetailDTO.toDomain(): MovieDetail {
    return MovieDetail(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        genres = this.genres.map { it.toDomain() },
        adult = this.adult,
        budget = this.budget,
        revenue = this.revenue,
        runtime = this.runtime,
        status = this.status,
        tagline = this.tagline,
    )
}

fun GenreDTO.toDomain(): Genre {
    return Genre(
        id = this.id,
        name = this.name,
    )
}
