package com.santimattius.list.domain

import com.santimattius.list.data.TodoListRepository

class UpdateTodoItem(private val repository: TodoListRepository) {
    suspend operator fun invoke(todoItem: TodoItem) = repository.updateTodoItem(todoItem)
}