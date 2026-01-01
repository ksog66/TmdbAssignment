package com.kklabs.themoviedb.di

import com.kklabs.themoviedb.database.MovieDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        MovieDatabase(
            get()
        )
    }
}