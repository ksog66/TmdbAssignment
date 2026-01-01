package com.kklabs.themoviedb.di

import com.kklabs.themoviedb.platformDomain.ApiConfigProvider
import com.kklabs.themoviedb.platformDomain.ApiConfigProviderImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val commonPlatformModule = module {
    single<ApiConfigProvider> { ApiConfigProviderImpl() }
}