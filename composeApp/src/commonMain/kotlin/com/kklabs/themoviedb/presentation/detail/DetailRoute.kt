package com.kklabs.themoviedb.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.kklabs.themoviedb.Constants
import com.kklabs.themoviedb.presentation.component.ErrorState
import com.kklabs.themoviedb.presentation.component.LoadingState
import com.kklabs.themoviedb.presentation.component.RatingBadge
import com.kklabs.themoviedb.utils.UiState
import com.kklabs.themoviedb.utils.formatNumber
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailRoute(
    movieId: Int,
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    val viewModel = koinViewModel<DetailViewModel>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetail(movieId)
    }

    when (val state = uiState) {
        is UiState.Success -> {
            DetailScreen(
                movieDetail = state.data,
                navController = navController,
                modifier = modifier
            )
        }
        is UiState.Error -> {
            ErrorState(
                message = state.message ?: "Something went wrong",
                onRetry = { viewModel.refresh(movieId) },
                modifier = modifier
            )
        }
        is UiState.Loading -> {
            LoadingState(modifier = modifier)
        }
    }
}

@Composable
fun DetailScreen(
    movieDetail: com.kklabs.themoviedb.domain.model.MovieDetail,
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {

    val backdropUrl = movieDetail.backdropPath?.let { "${Constants.IMAGE_BASE_URL}$it" }
    val posterUrl = movieDetail.posterPath?.let { "${Constants.IMAGE_BASE_URL}$it" }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                if (backdropUrl != null) {
                    AsyncImage(
                        model = backdropUrl,
                        contentDescription = movieDetail.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (posterUrl != null) {
                    AsyncImage(
                        model = backdropUrl,
                        contentDescription = movieDetail.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xFF1A1A1A)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )

                IconButton(
                    onClick = { navController?.popBackStack() },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChevronLeft,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movieDetail.title,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                    RatingBadge(rating = movieDetail.voteAverage)
                }

                if (!movieDetail.tagline.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movieDetail.tagline,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic
                    )
                }

                if (movieDetail.genres.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        movieDetail.genres.take(3).forEach { genre ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF2D2D2D))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = genre.name,
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (movieDetail.releaseDate.isNotEmpty()) {
                        Text(
                            text = "Release: ${movieDetail.releaseDate}",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    }
                    if (movieDetail.runtime != null) {
                        Text(
                            text = "${movieDetail.runtime} min",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Overview",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movieDetail.overview,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                if (movieDetail.budget > 0 || movieDetail.revenue > 0) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Additional Info",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (movieDetail.budget > 0) {
                        Text(
                            text = "Budget: $${movieDetail.budget.formatNumber()}",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 14.sp
                        )
                    }
                    if (movieDetail.revenue > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Revenue: $${movieDetail.revenue.formatNumber()}",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Status: ${movieDetail.status}",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }
        }
    }
}
