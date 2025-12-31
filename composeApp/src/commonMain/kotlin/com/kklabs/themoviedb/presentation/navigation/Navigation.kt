package com.kklabs.themoviedb.presentation.navigation

sealed class TmdbScreen(val route: String) {
    object Home : TmdbScreen("home")
    data class MovieList(val type: String) : TmdbScreen("movie_list/$type") {
        companion object Companion {
            const val routePattern = "movie_list/{type}"
            fun createRoute(type: String) = "movie_list/$type"
        }
    }
    data class MovieDetail(val id: Int) : TmdbScreen("movie_detail/$id") {
        companion object Companion {
            const val routePattern = "movie_detail/{id}"
            fun createRoute(id: Int) = "movie_detail/$id"
        }
    }
}


