package com.kklabs.themoviedb

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.kklabs.themoviedb.di.commonPlatformModule
import com.kklabs.themoviedb.di.databaseModule
import com.kklabs.themoviedb.di.domainModule
import com.kklabs.themoviedb.di.networkModule
import com.kklabs.themoviedb.di.platformModule
import com.kklabs.themoviedb.di.repositoryModule
import com.kklabs.themoviedb.di.viewModelModule
import com.kklabs.themoviedb.presentation.TmdbApp
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App(
    koinInit: KoinAppDeclaration = {}
) {
    MaterialTheme {
        KoinMultiplatformApplication(config = koinConfiguration{
            koinInit()
            modules(platformModule, commonPlatformModule, networkModule, databaseModule, repositoryModule, domainModule, viewModelModule)
        }) {
            TmdbApp()
        }
    }
}
