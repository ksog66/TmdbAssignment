package com.kklabs.themoviedb.platformDomain

interface ApiConfigProvider {
    fun getApiUrl(): String
    fun getBearerToken(): String
}

expect class ApiConfigProviderImpl(): ApiConfigProvider {
    override fun getApiUrl(): String
    override fun getBearerToken(): String
}