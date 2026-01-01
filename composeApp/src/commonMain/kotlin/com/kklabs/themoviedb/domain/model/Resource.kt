package com.kklabs.themoviedb.domain.model

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()

    data class Error<T>(val message: String, val cachedData:T? = null) : Resource<T>()
}