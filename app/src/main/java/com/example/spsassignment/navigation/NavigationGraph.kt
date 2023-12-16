package com.example.spsassignment.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spsassignment.ui.screens.TVDetailsScreen
import com.example.spsassignment.ui.screens.TvShowsScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = NavigationRoute.TV.route
    ) {
        val goBack: () -> Unit = {
            navController.popBackStack()
        }
        val goToTvDetails: (tvId: Int) -> Unit = { tvId ->
            navController.apply {
                currentBackStackEntry?.savedStateHandle?.set(
                    "tv_id", tvId
                )
                navigate(NavigationRoute.TV_DETAILS.route)
            }
        }

        slideComposable(NavigationRoute.TV.route) {
            TvShowsScreen(onTVClick = goToTvDetails)
        }

        slideComposable(NavigationRoute.TV_DETAILS.route) {
            val tvId =
                navController.previousBackStackEntry?.savedStateHandle?.get<Int?>("tv_id")
            TVDetailsScreen(
                tvId = tvId,
                onTVClick = goToTvDetails,
                onBackClick = goBack
            )
        }
        slideComposable(NavigationRoute.SEARCH.route) {
            navController.previousBackStackEntry?.savedStateHandle?.apply {
                val searchType = get<String>("search_type")
                val url = get<String>("url")
                searchType?.let { type ->
                    url?.let {
                    }
                }
            }
        }
    }

}

fun NavGraphBuilder.slideComposable(route: String, content: @Composable () -> Unit) {
    val animationSpec = tween<IntOffset>(700, easing = FastOutSlowInEasing)
    composable(route = route, content = {
        content()
    }, enterTransition = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = animationSpec
        )
    }, exitTransition = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = animationSpec
        )
    }, popEnterTransition = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = animationSpec
        )
    }, popExitTransition = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = animationSpec
        )
    })

}