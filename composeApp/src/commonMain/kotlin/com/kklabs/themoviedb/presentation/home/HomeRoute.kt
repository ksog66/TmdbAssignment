package com.kklabs.themoviedb.presentation.home

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kklabs.themoviedb.domain.model.vo.HomeData
import com.kklabs.themoviedb.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier
) {

    val viewModel = koinViewModel<HomeViewModel>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isRefreshing = uiState is UiState.Loading

    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refresh() },
        state = pullRefreshState,
    ) {
        when (val state = uiState) {
            is UiState.Success -> {
                HomeScreen(
                    homeData = state.data
                )
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = state.message ?: "Something Went Wrong")
                }
            }
            is UiState.Loading -> {
                Text("Loading")
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeData: HomeData
) {
    LazyColumn {
        items(homeData.popularMovies) {
            Text(text = it.title)
        }
    }
}