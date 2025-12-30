package com.kklabs.themoviedb.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val genres: List<Genre>,
    val adult: Boolean,
    val budget: Long,
    val revenue: Long,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
)