package com.kklabs.themoviedb.di

import com.kklabs.themoviedb.domain.usecase.GetMovieDetailUseCase
import com.kklabs.themoviedb.domain.usecase.GetNowPlayingMovieUseCase
import com.kklabs.themoviedb.domain.usecase.GetPopularMovieUseCase
import com.kklabs.themoviedb.domain.usecase.GetTopRatedMovieUseCase
import com.kklabs.themoviedb.domain.usecase.GetUpcomingMovieUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetNowPlayingMovieUseCase(get()) }
    factory { GetUpcomingMovieUseCase(get()) }
    factory { GetTopRatedMovieUseCase(get()) }
    factory { GetPopularMovieUseCase(get()) }
    factory { GetMovieDetailUseCase(get()) }
}