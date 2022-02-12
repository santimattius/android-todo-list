package com.santimattius.list.data

import com.santimattius.list.domain.TodoItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*

class TodoListRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    companion object {
        private val inMemoryTodoList by lazy { (1..10).map {
            TodoItem(id = UUID.randomUUID(), title = "Title $it", description = "Description $it")
        }.toMutableList() }
    }

    suspend fun getTodoList() = withContext(dispatcher) {
        delay(1_000)
        return@withContext inMemoryTodoList
    }

    suspend fun findTodoItem(id: String): TodoItem? = withContext(dispatcher) {
        delay(1_000)
        return@withContext inMemoryTodoList.firstOrNull { it.id.toString() == id }
    }
}
