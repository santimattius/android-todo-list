package com.santimattius.list.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.santimattius.list.ui.screen.HomeScreen
import com.santimattius.list.ui.screen.SplashScreen
import com.santimattius.list.ui.screen.TodoItemDetailScreen

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Navigation() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Route.Splash.route,
    ) {
        navDefinitions(navController)
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.navDefinitions(
    navController: NavController,
) {
    composable(Route.Splash) {
        SplashScreen(navigate = {
            navController.popBackStack()
            navController.navigate(Route.TodoList.route)
        })
    }
    composable(Route.TodoList.route) {
        HomeScreen()
    }
    composable(Route.TodoItem.route) {
        TodoItemDetailScreen()
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.composable(
    navItem: Route,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = navItem.route,
        arguments = navItem.args,
        enterTransition = { _, _ ->
            // Let's make for a really long fade in
            fadeIn(animationSpec = tween(1000))
        },
        exitTransition = { _, _ ->
            fadeOut(animationSpec = tween(1000))
        }
    ) {
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(arg: NavArguments): T {
    val value = arguments?.get(arg.key)
    requireNotNull(value)
    return value as T
}