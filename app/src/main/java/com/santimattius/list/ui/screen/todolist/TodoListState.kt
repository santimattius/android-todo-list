package com.santimattius.list.ui.screen.todolist

import com.santimattius.list.domain.TodoItem

data class TodoListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasError: Boolean = false,
    val data: List<TodoItem> = emptyList(),
)

val TodoListState.isEmpty: Boolean
    get() = data.isEmpty()
