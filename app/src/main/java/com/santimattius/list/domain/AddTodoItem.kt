package com.santimattius.list.domain

import com.santimattius.list.data.TodoListRepository

class AddTodoItem(private val repository: TodoListRepository) {
    suspend operator fun invoke(todoItem: TodoItem) = repository.addTodoItem(todoItem)
}