package com.kklabs.themoviedb.domain.model

data class MoviePage(
    val movies: List<Movie>,
    val currentPage: Int,
    val totalPages: Int
)