package com.kklabs.themoviedb.domain.model.vo

import com.kklabs.themoviedb.domain.model.Movie

data class HomeData(
    val upcomingMovies: List<Movie>,
    val nowPlayingMovies: List<Movie>,
    val popularMovies: List<Movie>,
    val topRatedMovies: List<Movie>
)