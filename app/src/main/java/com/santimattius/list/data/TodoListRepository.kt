package com.santimattius.list.data

import com.santimattius.list.domain.TodoItem
import java.util.*

class TodoListRepository(
    private val localDataSource: LocalDataSource,
) {

    fun getTodoList() = localDataSource.getAll()

    suspend fun findTodoItem(id: String): TodoItem? =
        localDataSource.find(UUID.fromString(id)).getOrNull()

    suspend fun addTodoItem(todoItem: TodoItem): Boolean {
        val result = localDataSource.save(todoItem)
        return result.isSuccess
    }

    suspend fun updateTodoItem(todoItem: TodoItem): Boolean {
        val result = localDataSource.update(todoItem)
        return result.isSuccess
    }

    suspend fun deleteTodoItem(todoItem: TodoItem): Boolean {
        val result = localDataSource.delete(todoItem)
        return result.isSuccess
    }
}
