package com.kklabs.themoviedb.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDTO(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val runtime: Int? = null,
    val budget: Long,
    val revenue: Long,
    val genres: List<GenreDTO>,
    val status: String,
    val tagline: String? = null,
    val adult: Boolean = false
)


