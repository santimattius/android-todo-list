package com.santimattius.list.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.santimattius.list.data.entity.TodoItemEntity

@Database(entities = [TodoItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class TodoDataBase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "todo_database"

        fun get(context: Context) =
            Room.databaseBuilder(context, TodoDataBase::class.java, DATABASE_NAME)
                .build()
    }

    abstract fun todoDao(): TodoDao
}