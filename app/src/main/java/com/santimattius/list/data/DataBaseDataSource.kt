package com.santimattius.list.data

import com.santimattius.list.data.database.TodoDao
import com.santimattius.list.data.entity.TodoItemEntity
import com.santimattius.list.domain.TodoItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.*

class DataBaseDataSource(
    private val dao: TodoDao,
    private val dispatcher: CoroutineDispatcher,
) : LocalDataSource {

    override fun getAll(): Flow<List<TodoItem>> = dao.getAll().map { it.asTodoItem() }

    override suspend fun save(todoItem: TodoItem): Result<Boolean> {
        return withContext(dispatcher) {
            try {
                dao.insertAll(todoItem.asEntity())
                Result.success(true)
            } catch (ex: Throwable) {
                Result.failure(SaveNoFound())
            }
        }
    }

    override suspend fun find(id: UUID): Result<TodoItem> {
        return withContext(dispatcher) {
            try {
                val todoItem = dao.findById(id.toString()).asTodoItem()
                Result.success(todoItem)
            } catch (ex: Throwable) {
                Result.failure(TodoItemNoExists())
            }
        }
    }

    override suspend fun delete(todoItem: TodoItem): Result<Boolean> {
        return withContext(dispatcher) {
            try {
                dao.delete(todoItem.asEntity())
                Result.success(true)
            } catch (ex: Throwable) {
                Result.failure(SaveNoFound())
            }
        }
    }

    override suspend fun update(todoItem: TodoItem): Result<Boolean> {
        return withContext(dispatcher) {
            try {
                dao.update(todoItem.asEntity())
                Result.success(true)
            } catch (ex: Throwable) {
                Result.failure(SaveNoFound())
            }
        }
    }
}

private fun TodoItemEntity.asTodoItem(): TodoItem {
    return TodoItem(
        id = UUID.fromString(this.id),
        title = this.title,
        description = this.description,
        date = this.date
    )
}

private fun TodoItem.asEntity(): TodoItemEntity {
    return TodoItemEntity(
        id = id.toString(),
        title = this.title,
        description = this.description,
        date = this.date,
        completed = false
    )
}

private fun List<TodoItemEntity>.asTodoItem(): List<TodoItem> {
    return map { entity -> entity.asTodoItem() }
}
