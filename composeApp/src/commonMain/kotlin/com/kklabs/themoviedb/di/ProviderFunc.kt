package com.kklabs.themoviedb.di

import com.kklabs.themoviedb.platformDomain.ApiConfigProvider
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


fun provideHttpClient(
    apiConfigProvider: ApiConfigProvider,
): HttpClient {
    return HttpClient {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = apiConfigProvider.getApiUrl()
            }

            header("Content-Type", "application/json;charset=utf-8")
            header("Connection", "keep-alive")
            header("Accept", "application/json")
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
        }

        install(Logging) {
            level = LogLevel.INFO
        }
    }
}
