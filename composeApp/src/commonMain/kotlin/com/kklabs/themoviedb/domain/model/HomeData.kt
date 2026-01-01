package com.kklabs.themoviedb.domain.model

import com.kklabs.themoviedb.domain.model.Movie

data class HomeData(
    val upcomingMovies: List<Movie>,
    val nowPlayingMovies: List<Movie>,
    val popularMovies: List<Movie>,
    val topRatedMovies: List<Movie>
) {
    fun isEmpty(): Boolean {
        return upcomingMovies.isEmpty() &&
                nowPlayingMovies.isEmpty() &&
                popularMovies.isEmpty() &&
                topRatedMovies.isEmpty()
    }

}