package com.santimattius.list.ui.screen.todoitem

import com.santimattius.list.domain.TodoItem

data class TodoItemScreenState(
    val isLoading: Boolean = false,
    val withError: Boolean = false,
    val actionType: ActionType = ActionType.CREATE,
    val todoItem: TodoItem = TodoItem.empty(),
    val close: Boolean = false,
) {
    companion object {
        fun initial() = TodoItemScreenState(isLoading = true, )
    }
}
