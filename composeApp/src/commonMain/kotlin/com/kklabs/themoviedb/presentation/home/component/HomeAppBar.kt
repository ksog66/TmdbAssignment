package com.kklabs.themoviedb.presentation.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kklabs.themoviedb.presentation.theme.Colors

@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    isOnline: Boolean,
    isSearchOpen: Boolean,
    openSearch: () -> Unit,
    closeSearch: () -> Unit,
    clearSearchText: () -> Unit,
    onSearchQueryChange:(String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Colors.darkBackground),
        contentAlignment = Alignment.CenterStart
    ) {
        if (!isSearchOpen) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.darkBackground)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tmdb",
                    color = Colors.darkOnSurface,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                if (isOnline) {
                    IconButton(onClick = openSearch) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Colors.darkOnSurface,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isSearchOpen,
            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.darkBackground)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { 
                        Text(
                            "Search movies...", 
                            color = Colors.darkOnSurfaceVariant
                        ) 
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Colors.darkSurface,
                        unfocusedContainerColor = Colors.darkSurface,
                        focusedTextColor = Colors.darkOnSurface,
                        unfocusedTextColor = Colors.darkOnSurface,
                        focusedIndicatorColor = Colors.transparent,
                        unfocusedIndicatorColor = Colors.transparent,
                        focusedPlaceholderColor = Colors.darkOnSurfaceVariant,
                        unfocusedPlaceholderColor = Colors.darkOnSurfaceVariant
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    leadingIcon = {
                        IconButton(onClick = closeSearch) {
                            Icon(
                                imageVector = Icons.Default.ChevronLeft,
                                contentDescription = "Back",
                                tint = Colors.darkOnSurfaceVariant
                            )
                        }
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = clearSearchText) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = Colors.darkOnSurfaceVariant
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}