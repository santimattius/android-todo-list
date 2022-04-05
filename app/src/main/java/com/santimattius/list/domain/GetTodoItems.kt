package com.santimattius.list.domain

import com.santimattius.list.data.TodoListRepository

class GetTodoItems(private val repository: TodoListRepository) {
    operator fun invoke() = repository.getTodoList()
}
