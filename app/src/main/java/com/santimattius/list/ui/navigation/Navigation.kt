package com.santimattius.list.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.santimattius.list.ui.screen.SplashRoute
import com.santimattius.list.ui.screen.todoitem.TodoItemDetailRoute
import com.santimattius.list.ui.screen.todoitem.TodoItemDialogRoute
import com.santimattius.list.ui.screen.todolist.TodoListRoute

@ExperimentalComposeUiApi
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
                width = constraints.maxHeight.div(2)
            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.navDefinitions(
    navController: NavController,
    width: Int,
) {
    composable(
        route = Route.Splash.route,
        content = {
            SplashRoute(navigate = {
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
            TodoListRoute { item, newFlow ->
                if (newFlow) {
                    navController.navigate(Route.TodoItemDialog.createRoute(item.id.toString()))
                } else {
                    navController.navigate(Route.TodoItem.createRoute(item.id.toString()))
                }
            }
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300))
        },
    )
    composable(
        route = Route.TodoItem.route,
        arguments = Route.TodoItem.args,
        content = {
            TodoItemDetailRoute {
                navController.popBackStack()
            }
        },
        enterTransition = {
            slideInVertically(
                initialOffsetY = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    )
    dialog(
        route = Route.TodoItemDialog.route,
        arguments = Route.TodoItemDialog.args
    ) {
        TodoItemDialogRoute {
            navController.popBackStack()
        }
    }
}