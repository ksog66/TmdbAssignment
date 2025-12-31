package com.kklabs.themoviedb.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kklabs.themoviedb.presentation.home.HomeRoute
import com.kklabs.themoviedb.presentation.navigation.TmdbScreen
import com.kklabs.themoviedb.presentation.theme.Colors.white1

@Composable
fun TmdbApp(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(), color = white1
    ) {
        val navController = rememberNavController()
        TmdbNavHost(
            navController = navController
        )
    }
}

@Composable
fun TmdbNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = TmdbScreen.Home.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = TmdbScreen.Home.route,
        ) {
            HomeRoute(modifier = modifier)
        }

        composable(
            route = TmdbScreen.MovieList.routePattern,
        ) {
            HomeRoute(modifier = modifier)
        }

        composable(
            route = TmdbScreen.MovieDetail.routePattern,
        ) {
            HomeRoute(modifier = modifier)
        }
    }
}