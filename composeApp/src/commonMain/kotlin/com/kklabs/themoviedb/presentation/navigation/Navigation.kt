package com.kklabs.themoviedb.presentation.navigation


import kotlinx.serialization.Serializable

sealed interface TmdbScreen {

    @Serializable
    data object Home : TmdbScreen

    @Serializable
    data class MovieList(
        val type: String
    ) : TmdbScreen

    @Serializable
    data class MovieDetail(
        val id: Int
    ) : TmdbScreen
}


