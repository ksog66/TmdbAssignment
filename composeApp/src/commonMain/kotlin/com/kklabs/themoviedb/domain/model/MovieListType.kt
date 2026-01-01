package com.kklabs.themoviedb.domain.model

enum class MovieListType(val displayName: String) {

    NOW_PLAYING("Now Playing"),
    POPULAR("Popular"),
    TOP_RATED("Top Rated"),
    UPCOMING("Upcoming");

    companion object {
        fun fromString(type: String): MovieListType {
            return when (type.lowercase()) {
                "now_playing" -> NOW_PLAYING
                "popular" -> POPULAR
                "top_rated", "toprated" -> TOP_RATED
                "upcoming" -> UPCOMING
                else -> POPULAR
            }
        }
    }
}