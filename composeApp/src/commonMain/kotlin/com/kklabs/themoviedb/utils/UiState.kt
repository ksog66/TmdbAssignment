package com.kklabs.themoviedb.utils

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T, val fromCache: Boolean = false) : UiState<T>()
    data class Error<T>(val message: String?, val cachedData: T? = null) : UiState<T>()
}


