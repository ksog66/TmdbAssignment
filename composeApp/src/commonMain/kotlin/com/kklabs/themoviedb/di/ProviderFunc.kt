package com.kklabs.themoviedb.di

import com.kklabs.themoviedb.platformDomain.ApiConfigProvider
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
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
            header("Authorization", "Bearer ${apiConfigProvider.getBearerToken()}")
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
            requestTimeoutMillis = 3_000
            connectTimeoutMillis = 3_000

            socketTimeoutMillis = 3_000
        }

        install(Logging) {
            level = LogLevel.ALL

            logger = object : Logger {
                override fun log(message: String) {
                    println("NetworkLog: $message")
                }
            }
        }
    }
}
