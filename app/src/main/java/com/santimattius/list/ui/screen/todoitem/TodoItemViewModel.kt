package com.santimattius.list.ui.screen.todoitem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.list.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TodoItemViewModel @Inject constructor(
    private val findTodoItem: FindTodoItem,
    private val addTodoItem: AddTodoItem,
    private val updateTodoItem: UpdateTodoItem,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var job: Job? = null

    var state by mutableStateOf(TodoItemScreenState.initial())
        private set

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        showError()
    }

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            load(id)
        } ?: showError()
    }

    private fun load(id: String) {
        state = state.copy(isLoading = true)
        job?.cancel()
        job = viewModelScope.launch(coroutineExceptionHandler) {
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

    fun save() {
        val todoItem = state.todoItem
        if (todoItem.isEmpty()) {
            state = TodoItemScreenState(isEmpty = true)
        } else {
            job?.cancel()
            job = viewModelScope.launch(coroutineExceptionHandler) {
                val isSuccess = when (state.actionType) {
                    ActionType.EDIT -> {
                        updateTodoItem(todoItem)
                    }
                    ActionType.CREATE -> {
                        addTodoItem(todoItem)
                    }
                }
                state = state.copy(close = isSuccess)
            }
        }
    }
}