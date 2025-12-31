package com.kklabs.themoviedb

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.kklabs.themoviedb.di.commonPlatformModule
import com.kklabs.themoviedb.di.domainModule
import com.kklabs.themoviedb.di.networkModule
import com.kklabs.themoviedb.di.repositoryModule
import com.kklabs.themoviedb.di.viewModelModule
import com.kklabs.themoviedb.presentation.TmdbApp
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    MaterialTheme {
        KoinMultiplatformApplication(config = koinConfiguration{
            modules(commonPlatformModule, networkModule, repositoryModule, domainModule, viewModelModule)
        }) {
            TmdbApp()
        }
    }
}
