package com.santimattius.list.data.database

import androidx.room.*
import com.santimattius.list.data.entity.TodoItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<TodoItemEntity>>

    @Query("SELECT * FROM todo WHERE id=:id")
    suspend fun findById(id: String): TodoItemEntity

    @Insert
    suspend fun insertAll(vararg todos: TodoItemEntity)

    @Delete
    suspend fun delete(todo: TodoItemEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(todo: TodoItemEntity)

}