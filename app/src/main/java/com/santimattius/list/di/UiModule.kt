package com.santimattius.list.di

import com.santimattius.list.data.TodoListRepository
import com.santimattius.list.domain.AddTodoItem
import com.santimattius.list.domain.FindTodoItem
import com.santimattius.list.domain.GetTodoItems
import com.santimattius.list.domain.UpdateTodoItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UiModule {

    @Provides
    @ViewModelScoped
    fun provideGetTodoItems(repository: TodoListRepository) = GetTodoItems(repository)

    @Provides
    @ViewModelScoped
    fun provideFindTodoItem(repository: TodoListRepository) = FindTodoItem(repository)

    @Provides
    @ViewModelScoped
    fun provideAddTodoItem(repository: TodoListRepository) = AddTodoItem(repository)

    @Provides
    @ViewModelScoped
    fun provideUpdateTodoItem(repository: TodoListRepository) = UpdateTodoItem(repository)
}