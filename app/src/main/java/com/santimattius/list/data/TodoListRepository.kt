package com.santimattius.list.data

import com.santimattius.list.domain.TodoItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.*

class TodoListRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    private val mutex = Mutex()
    private val inMemoryTodoList by lazy {
        (1..10).map {
            val instance = Calendar.getInstance()
            instance.add(Calendar.DAY_OF_MONTH, -it)
            TodoItem(id = UUID.randomUUID(),
                title = "Title $it",
                description = "Description $it",
                date = instance.time
            )
        }.toMutableList()
    }

    suspend fun getTodoList() = withContext(dispatcher) {
        delay(1_000)
        mutex.withLock {
            val todoItems = inMemoryTodoList
            todoItems.sortByDescending { it.date }
            return@withContext todoItems
        }
    }

    suspend fun findTodoItem(id: String): TodoItem? = withContext(dispatcher) {
        delay(1_000)
        mutex.withLock {
            return@withContext inMemoryTodoList.firstOrNull { it.id.toString() == id }
        }
    }

    suspend fun addTodoItem(todoItem: TodoItem): Boolean {
        delay(1_000)
        mutex.withLock {
            return inMemoryTodoList.add(todoItem)
        }
    }

    suspend fun updateTodoItem(todoItem: TodoItem): Boolean {
        delay(1_000)
        mutex.withLock {
            val itemIndex = inMemoryTodoList.indexOfFirst { it.id == todoItem.id }
            if (itemIndex == -1) return false
            inMemoryTodoList.removeAt(itemIndex)
            return inMemoryTodoList.add(todoItem)
        }
    }

    suspend fun deleteTodoItem(todoItem: TodoItem): Boolean {
        delay(1_000)
        mutex.withLock {
            val itemIndex = inMemoryTodoList.indexOfFirst { it.id == todoItem.id }
            if (itemIndex == -1) return false
            inMemoryTodoList.removeAt(itemIndex)
            return true
        }
    }
}
