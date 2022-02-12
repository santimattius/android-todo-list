package com.santimattius.list.domain

import com.santimattius.list.data.TodoListRepository

class FindTodoItem(private val repository: TodoListRepository) {
    suspend operator fun invoke(id: String) = repository.findTodoItem(id)
}
