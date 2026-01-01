package com.kklabs.themoviedb.presentation.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kklabs.themoviedb.domain.model.Movie
import com.kklabs.themoviedb.presentation.component.MovieCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCarousel(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { movies.size })

    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            MovieCard(
                movie = movies[page],
                isCarousel = true,
                onClick = { onMovieClick(movies[page].id) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            repeat(movies.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.White else Color.White.copy(alpha = 0.5f)
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(8.dp)
                        .background(color, CircleShape)
                )
            }
        }
    }
}

