package com.santimattius.list.data

import com.santimattius.list.domain.TodoItem
import kotlinx.coroutines.flow.Flow
import java.util.*

interface LocalDataSource {

    fun getAll(): Flow<List<TodoItem>>

    suspend fun save(todoItem: TodoItem): Result<Boolean>

    suspend fun find(id: UUID): Result<TodoItem>

    suspend fun delete(todoItem: TodoItem): Result<Boolean>

    suspend fun update(todoItem: TodoItem): Result<Boolean>
}