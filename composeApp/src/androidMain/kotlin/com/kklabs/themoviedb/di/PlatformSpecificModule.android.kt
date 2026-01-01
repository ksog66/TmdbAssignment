package com.kklabs.themoviedb.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.kklabs.themoviedb.database.MovieDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = MovieDatabase.Schema,
            context = get(),
            name = "movie.db"
        )
    }
}