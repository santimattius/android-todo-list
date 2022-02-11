package com.santimattius.list.di

import com.santimattius.list.data.TodoListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideRepository(): TodoListRepository = TodoListRepository()
}