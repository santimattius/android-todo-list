package com.santimattius.list.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.santimattius.list.domain.TodoItem

class TodoViewModel : ViewModel() {

    // state: todoItems
//    private var _todoItems = MutableLiveData(listOf<TodoItem>())
//    val todoItems: LiveData<List<TodoItem>> = _todoItems
    // state: todoItems
    var todoItems = mutableStateListOf<TodoItem>()
        private set


    // event: addItem
    fun addItem(item: TodoItem) {
        todoItems.add(item)
    }

    // event: removeItem
    fun removeItem(item: TodoItem) {
        todoItems.remove(item)
    }

}