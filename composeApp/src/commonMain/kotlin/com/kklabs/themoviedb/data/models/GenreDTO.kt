package com.kklabs.themoviedb.data.models

import kotlinx.serialization.Serializable

@Serializable
data class GenreDTO(
    val id: Int,
    val name: String
)


