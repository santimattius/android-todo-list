package com.santimattius.list.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.santimattius.list.ui.screen.SplashScreen
import com.santimattius.list.ui.screen.todoitem.TodoItemDetailScreen
import com.santimattius.list.ui.screen.todolist.TodoListScreen

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Navigation() {
    BoxWithConstraints {
        val navController = rememberAnimatedNavController()

        AnimatedNavHost(
            navController = navController,
            startDestination = Route.Splash.route,
        ) {
            navDefinitions(
                navController = navController,
                width = constraints.maxWidth / 2
            )
        }
    }
}

//TODO: add animations
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.navDefinitions(
    navController: NavController,
    width: Int,
) {
    composable(
        route = Route.Splash,
        content = {
            SplashScreen(navigate = {
                with(navController) {
                    popBackStack()
                    navigate(Route.TodoList.route)
                }
            })
        }
    )
    composable(
        route = Route.TodoList.route,
        content = {
            TodoListScreen {
                navController.navigate(Route.TodoItem.createRoute(it.id.toString()))
            }
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
    )
    composable(
        route = Route.TodoItem.route,
        content = {
            TodoItemDetailScreen {
                navController.popBackStack()
            }
        },
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    )
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.composable(
    route: Route,
    content: @Composable (NavBackStackEntry) -> Unit,
    enterTransition: () -> EnterTransition? = { null },
    exitTransition: () -> ExitTransition? = { null },
    popEnterTransition: () -> EnterTransition? = enterTransition,
    popExitTransition: () -> ExitTransition? = exitTransition,
) {
    composable(
        route = route.route,
        arguments = route.args,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(arg: NavArguments): T {
    val value = arguments?.get(arg.key)
    requireNotNull(value)
    return value as T
}