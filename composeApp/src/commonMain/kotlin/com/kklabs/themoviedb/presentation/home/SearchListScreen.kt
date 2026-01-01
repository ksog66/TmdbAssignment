package com.kklabs.themoviedb.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kklabs.themoviedb.domain.model.Movie
import com.kklabs.themoviedb.presentation.list.component.HorizontalMovieCard
import com.kklabs.themoviedb.presentation.theme.Colors

@Composable
fun SearchListScreen(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    isLoading: Boolean,
    errorMessage: String? = null,
    searchQuery: String = "",
    onMovieClick: (Int) -> Unit,
    onLoadMore: () -> Unit
) {
    if (movies.isEmpty() && !isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Colors.darkBackground),
            contentAlignment = Alignment.Center
        ) {
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = Colors.darkOnSurface
                )
            } else if (searchQuery.isNotEmpty()) {
                Text(
                    text = "No results found",
                    color = Colors.darkOnSurfaceVariant
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Colors.darkBackground)
        ) {
            items(
                count = movies.size,
                key = { index -> movies[index].id }
            ) { index ->
                val movie = movies[index]

                if (index >= movies.size - 3 && !isLoading) {
                    LaunchedEffect(Unit) { onLoadMore() }
                }

                HorizontalMovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Colors.darkPrimary
                        )
                    }
                }
            }
        }
    }
}