package com.kklabs.themoviedb.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.kklabs.themoviedb.domain.model.Movie
import com.kklabs.themoviedb.domain.model.MovieListType
import com.kklabs.themoviedb.presentation.component.ErrorState
import com.kklabs.themoviedb.presentation.component.LoadingState
import com.kklabs.themoviedb.presentation.list.component.HorizontalMovieCard
import com.kklabs.themoviedb.presentation.theme.Colors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ListRoute(
    listType: MovieListType,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel = koinViewModel<ListViewModel>()

    val pagedMovies = remember(listType) {
        viewModel.getPagedMovies(listType)
    }.collectAsLazyPagingItems()

    val refreshState = pagedMovies.loadState.refresh

    Box(modifier = modifier.fillMaxSize().background(Color(0xFF1A1A1A)).windowInsetsPadding(WindowInsets.statusBars)) {
        when {
            refreshState is LoadState.Loading && pagedMovies.itemCount == 0 -> {
                LoadingState(modifier = Modifier.fillMaxSize())
            }

            refreshState is LoadState.Error && pagedMovies.itemCount == 0 -> {
                ErrorState(
                    message = (refreshState as LoadState.Error).error.message ?: "Unknown error",
                    onRetry = { pagedMovies.retry() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                ListScreen(
                    pagedMovies = pagedMovies,
                    title = listType.displayName,
                    onMovieClick = navigateToDetail,
                    onBackClick = onBackClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ListScreen(
    pagedMovies: LazyPagingItems<Movie>,
    title: String,
    onMovieClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .background(Colors.darkBackground),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        items(
            count = pagedMovies.itemCount,
            key = pagedMovies.itemKey { it.id }
        ) { index ->
            val movie = pagedMovies[index]
            if (movie != null) {
                HorizontalMovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }

        val appendState = pagedMovies.loadState.append

        if (appendState is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        } else if (appendState is LoadState.Error) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TextButton(onClick = { pagedMovies.retry() }) {
                        Text(
                            text = "Error loading more. Tap to Retry.",
                            color = Color(0xFF1E90FF)
                        )
                    }
                }
            }
        }
    }
}