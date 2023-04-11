package com.santimattius.list.ui.screen.todolist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.santimattius.list.domain.GetTodoItems
import com.santimattius.list.domain.RemoveTodoItem
import com.santimattius.list.domain.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodoItems: GetTodoItems,
    private val removeTodoItem: RemoveTodoItem,
) : ViewModel() {

    var state by mutableStateOf(TodoListState())
        private set

    init {
        loadTodoList()
    }

    private fun loadTodoList() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val newFlowEnable = FirebaseRemoteConfig.getInstance().getBoolean("dialog_feature")
            getTodoItems().collect { items ->
                state = state.copy(isLoading = false, data = items, newFlowEnable = newFlowEnable)
            }
        }

    }

    fun removeItem(item: TodoItem) {
        viewModelScope.launch {
            removeTodoItem(item)
        }
    }

    fun refresh() {
        state = state.copy(isRefreshing = true)
        viewModelScope.launch {
            state = state.copy(isRefreshing = false)
        }
    }

    fun report(){
        throw RuntimeException("First Test Crash")
    }
}