package com.kklabs.themoviedb.di

import com.kklabs.themoviedb.presentation.detail.MovieDetailViewModel
import com.kklabs.themoviedb.presentation.home.HomeViewModel
import com.kklabs.themoviedb.presentation.list.ListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(),get()) }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { ListViewModel(get()) }
}