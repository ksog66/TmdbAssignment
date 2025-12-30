package com.kklabs.themoviedb.platformDomain

import com.kklabs.themoviedb.BuildConfig

actual class ApiConfigProviderImpl: ApiConfigProvider {
    actual override fun getApiUrl(): String {
        return BuildConfig.BASE_URL
    }
    actual override fun getBearerToken(): String {
        return BuildConfig.BEARER_TOKEN
    }
}