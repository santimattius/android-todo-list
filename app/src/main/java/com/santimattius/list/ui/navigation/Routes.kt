package com.santimattius.list.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Route(
    protected val definition: String,
    private val arguments: List<NavArguments> = emptyList(),
) {

    object Splash : Route(definition = "splash")
    object TodoList : Route(definition = "todo")
    object TodoItem : Route(definition = "todo_detail", arguments = listOf(NavArguments.Id)) {
        fun createRoute(id: String) = "$definition/$id"

    }

    object TodoItemDialog : Route(definition = "todo_dialog", arguments = listOf(NavArguments.Id)) {
        fun createRoute(id: String) = "$definition/$id"

    }

    val route = run {
        val argValues = arguments.map { "{${it.key}}" }
        listOf(definition)
            .plus(argValues)
            .joinToString("/")
    }

    val args = arguments.map {
        navArgument(it.key) { type = it.navType }
    }
}


enum class NavArguments(val key: String, val navType: NavType<*>) {
    Id(key = "id", navType = NavType.StringType);
}