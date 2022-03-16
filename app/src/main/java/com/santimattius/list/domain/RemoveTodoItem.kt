package com.santimattius.list.domain

import com.santimattius.list.data.TodoListRepository

class RemoveTodoItem(private val repository: TodoListRepository) {

    suspend operator fun invoke(todoItem: TodoItem) = repository.deleteTodoItem(todoItem)
}
