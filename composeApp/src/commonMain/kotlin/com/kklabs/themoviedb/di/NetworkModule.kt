package com.kklabs.themoviedb.di

import com.kklabs.themoviedb.data.remote.NetworkDataSourceImpl
import com.kklabs.themoviedb.domain.NetworkDataSource
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {

    single {
        provideHttpClient(apiConfigProvider = get())
    }

    single<NetworkDataSource> {
        NetworkDataSourceImpl(httpClient = get())
    } bind (NetworkDataSource::class)
}