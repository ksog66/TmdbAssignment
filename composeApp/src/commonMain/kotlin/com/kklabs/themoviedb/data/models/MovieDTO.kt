package com.kklabs.themoviedb.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val genreIds: List<Int> = emptyList(),
    val adult: Boolean = false
)


