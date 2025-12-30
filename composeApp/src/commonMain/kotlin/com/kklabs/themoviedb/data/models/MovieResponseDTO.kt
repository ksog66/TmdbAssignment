package com.kklabs.themoviedb.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val totalPages: Int,
    val totalResults: Int
)


