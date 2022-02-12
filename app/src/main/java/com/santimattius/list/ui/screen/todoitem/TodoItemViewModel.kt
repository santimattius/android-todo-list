package com.santimattius.list.ui.screen.todoitem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.list.domain.FindTodoItem
import com.santimattius.list.domain.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TodoItemViewModel @Inject constructor(
    private val findTodoItem: FindTodoItem,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var job: Job? = null

    var state by mutableStateOf(TodoItemScreenState.initial())
        private set

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            load(id)
        } ?: showError()
    }

    private fun load(id: String) {
        job?.cancel()
        state = state.copy(isLoading = true)
        job = viewModelScope.launch {
            val item = findTodoItem(id)
            state = if (item == null) {
                state.copy(
                    isLoading = false,
                    actionType = ActionType.CREATE,
                    todoItem = TodoItem.empty().copy(id = UUID.fromString(id))
                )
            } else {
                state.copy(
                    isLoading = false,
                    actionType = ActionType.EDIT,
                    todoItem = item
                )
            }
        }
    }

    fun update(todoItem: TodoItem) {
        state = state.copy(todoItem = todoItem)
    }

    private fun showError() {
        state = state.copy(isLoading = false, withError = true)
    }
}