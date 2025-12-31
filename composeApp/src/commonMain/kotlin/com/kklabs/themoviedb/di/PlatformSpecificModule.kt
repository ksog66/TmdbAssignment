package com.kklabs.themoviedb.di

import com.kklabs.themoviedb.platformDomain.ApiConfigProvider
import com.kklabs.themoviedb.platformDomain.ApiConfigProviderImpl
import org.koin.dsl.module

val commonPlatformModule = module {
    single<ApiConfigProvider> { ApiConfigProviderImpl() }
}