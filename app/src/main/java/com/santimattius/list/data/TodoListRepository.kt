package com.santimattius.list.data

import com.santimattius.list.domain.TodoItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*

class TodoListRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun getTodoList() = withContext(dispatcher) {
        delay(1_000)
        return@withContext (1..10).map {
            TodoItem(id = UUID.randomUUID(), title = "Title $it", description = "Description $it")
        }
    }
}
