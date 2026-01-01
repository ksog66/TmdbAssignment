package com.kklabs.themoviedb.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kklabs.themoviedb.domain.model.HomeData
import com.kklabs.themoviedb.presentation.component.CacheIndicator
import com.kklabs.themoviedb.presentation.component.ErrorState
import com.kklabs.themoviedb.presentation.component.LoadingState
import com.kklabs.themoviedb.presentation.home.component.HomeAppBar
import com.kklabs.themoviedb.presentation.home.component.MovieCarousel
import com.kklabs.themoviedb.presentation.home.component.MovieSection
import com.kklabs.themoviedb.presentation.theme.Colors
import com.kklabs.themoviedb.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    navigateToList: (String) -> Unit = {}
) {
    val viewModel = koinViewModel<HomeViewModel>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    val isHomeRefreshing = uiState is UiState.Loading

    val pullRefreshState = rememberPullToRefreshState()

    val isOnline = (uiState as? UiState.Success)?.fromCache == false
    Scaffold(
        modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.statusBars),
        containerColor = Colors.darkBackground,
        topBar = {
            HomeAppBar(
                modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                searchQuery = searchState.query,
                isOnline = isOnline,
                isSearchOpen = searchState.isActive,
                openSearch = viewModel::openSearch,
                closeSearch = viewModel::closeSearch,
                clearSearchText = viewModel::clearSearchText,
                onSearchQueryChange = viewModel::onSearchQueryChange,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Colors.darkBackground)
                .pullToRefresh(
                    isRefreshing = isHomeRefreshing,
                    state = pullRefreshState,
                    onRefresh = { viewModel.refresh() })
        ) {
            if (searchState.isActive) {
                SearchListScreen(
                    movies = searchState.results,
                    isLoading = searchState.isLoading,
                    errorMessage = searchState.error,
                    searchQuery = searchState.query,
                    onMovieClick = navigateToDetail,
                    onLoadMore = { viewModel.loadNextSearchPage() },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                when (val state = uiState) {
                    is UiState.Success -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            HomeScreen(
                                homeData = state.data,
                                navigateToDetail = navigateToDetail,
                                navigateToList = navigateToList
                            )
                            if (state.fromCache) {
                                CacheIndicator(
                                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                                )
                            }
                        }
                    }

                    is UiState.Error -> {
                        ErrorState(
                            modifier = Modifier.fillMaxSize(),
                            onRetry = {viewModel.refresh()},
                            message = state.message ?: "Something Went Wrong"
                        )
                    }

                    is UiState.Loading -> {
                        LoadingState()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeData: HomeData,
    navigateToDetail: (Int) -> Unit,
    navigateToList: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Colors.darkBackground)
    ) {
        item {
            if (homeData.nowPlayingMovies.isNotEmpty()) {
                MovieCarousel(
                    movies = homeData.nowPlayingMovies,
                    onMovieClick = navigateToDetail,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            if (homeData.popularMovies.isNotEmpty()) {
                MovieSection(
                    title = "Popular",
                    movies = homeData.popularMovies,
                    onMovieClick = navigateToDetail,
                    onSeeAllClick = {
                        navigateToList("popular")
                    }
                )
            }
        }

        item {
            if (homeData.topRatedMovies.isNotEmpty()) {
                MovieSection(
                    title = "Top Rated",
                    movies = homeData.topRatedMovies,
                    onMovieClick = navigateToDetail,
                    onSeeAllClick = {
                        navigateToList("top_rated")
                    }
                )
            }
        }

        item {
            if (homeData.upcomingMovies.isNotEmpty()) {
                MovieSection(
                    title = "Upcoming",
                    movies = homeData.upcomingMovies,
                    onMovieClick = navigateToDetail,
                    onSeeAllClick = {
                        navigateToList("upcoming")
                    }
                )
            }
        }
    }
}