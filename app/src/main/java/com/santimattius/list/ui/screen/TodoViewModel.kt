package com.santimattius.list.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.list.domain.GetTodoItems
import com.santimattius.list.domain.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodoItems: GetTodoItems,
) : ViewModel() {

    private var job: Job? = null

    var state by mutableStateOf(TodoListState())
        private set

    init {
        loadTodoList()
    }

    private fun loadTodoList() {
        state = state.copy(isLoading = true)
        job?.cancel()
        job = viewModelScope.launch {
            val items = getTodoItems()
            state = state.copy(isLoading = false, data = items)
        }
    }

    // event: addItem
    fun addItem(item: TodoItem) {

    }

    // event: removeItem
    fun removeItem(item: TodoItem) {

    }

}