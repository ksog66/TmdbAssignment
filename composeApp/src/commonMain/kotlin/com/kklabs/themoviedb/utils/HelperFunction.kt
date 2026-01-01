package com.kklabs.themoviedb.utils

import kotlin.math.roundToInt

fun Long.formatNumber(): String {
    return when {
        this >= 1_000_000_000 -> {
            val value = this / 1_000_000_000.0
            "${(value * 100).roundToInt() / 100.0}B"
        }
        this >= 1_000_000 -> {
            val value = this / 1_000_000.0
            "${(value * 100).roundToInt() / 100.0}M"
        }
        this >= 1_000 -> {
            val value = this / 1_000.0
            "${(value * 100).roundToInt() / 100.0}K"
        }
        else -> this.toString()
    }
}