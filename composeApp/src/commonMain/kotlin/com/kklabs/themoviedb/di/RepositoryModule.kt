package com.kklabs.themoviedb.di

import com.kklabs.themoviedb.data.repository.MoviesRepositoryImpl
import com.kklabs.themoviedb.domain.repo.MoviesRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(get())
    } bind (MoviesRepository::class)
}