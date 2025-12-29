package com.kklabs.themoviedb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform