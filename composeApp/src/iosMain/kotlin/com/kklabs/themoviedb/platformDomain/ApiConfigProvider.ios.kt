package com.kklabs.themoviedb.platformDomain

import com.kklabs.themoviedb.utils.NsBundleConfig

actual class ApiConfigProviderImpl actual constructor(): ApiConfigProvider {
    actual override fun getApiUrl(): String = NsBundleConfig.getValue("BASE_URL")
    actual override fun getBearerToken(): String = NsBundleConfig.getValue("BEARER_TOKEN")
}