package com.kklabs.themoviedb.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun RatingBadge(
    rating: Double,
    modifier: Modifier = Modifier
) {
    val color = when {
        rating >= 8.0 -> Color(0xFF4CAF50)
        rating >= 6.0 -> Color(0xFFFF9800)
        else -> Color(0xFFF44336)
    }

    val formattedRating = (rating * 10.0).roundToInt() / 10.0

    Box(
        modifier = modifier
            .background(color, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = formattedRating.toString(),
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}





