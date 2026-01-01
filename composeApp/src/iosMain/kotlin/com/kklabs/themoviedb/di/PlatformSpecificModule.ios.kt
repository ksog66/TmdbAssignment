package com.kklabs.themoviedb.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.kklabs.themoviedb.database.MovieDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> {
        NativeSqliteDriver(MovieDatabase.Schema, "movie.db")
    }
}