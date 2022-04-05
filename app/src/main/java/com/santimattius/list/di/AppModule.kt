package com.santimattius.list.di

import android.app.Application
import com.santimattius.list.data.DataBaseDataSource
import com.santimattius.list.data.LocalDataSource
import com.santimattius.list.data.database.TodoDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataBase(application: Application): TodoDataBase = TodoDataBase.get(application)

    @Provides
    fun provideLocalDataSource(db: TodoDataBase): LocalDataSource =
        DataBaseDataSource(db.todoDao(), Dispatchers.IO)

}