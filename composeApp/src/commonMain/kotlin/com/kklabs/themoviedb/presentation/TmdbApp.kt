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
import androidx.navigation.toRoute
import com.kklabs.themoviedb.presentation.detail.DetailRoute
import com.kklabs.themoviedb.presentation.home.HomeRoute
import com.kklabs.themoviedb.presentation.list.ListRoute
import com.kklabs.themoviedb.domain.model.MovieListType
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
        startDestination = TmdbScreen.Home,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable<TmdbScreen.Home> {
            HomeRoute(
                modifier = modifier,
                navigateToDetail = {
                    navController.navigate(TmdbScreen.MovieDetail(id = it))
                },
                navigateToList = { type ->
                    navController.navigate(TmdbScreen.MovieList(type = type))
                }
            )
        }

        composable<TmdbScreen.MovieList> { backStackEntry ->
            val args = backStackEntry.toRoute<TmdbScreen.MovieList>()
            val listType = MovieListType.fromString(args.type)
            
            ListRoute(
                listType = listType,
                modifier = modifier,
                navigateToDetail = {
                    navController.navigate(TmdbScreen.MovieDetail(id = it))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<TmdbScreen.MovieDetail> { backStackEntry ->

            val args = backStackEntry.toRoute<TmdbScreen.MovieDetail>()

            DetailRoute(
                movieId = args.id,
                modifier = modifier,
                navController = navController
            )
        }
    }
}