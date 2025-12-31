package com.kklabs.themoviedb.di

import com.kklabs.themoviedb.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(),get(),get(),get()) }
}