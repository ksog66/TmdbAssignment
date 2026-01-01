package com.kklabs.themoviedb.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CacheIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF3CD))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Showing cached data",
            color = Color(0xFF856404),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}





