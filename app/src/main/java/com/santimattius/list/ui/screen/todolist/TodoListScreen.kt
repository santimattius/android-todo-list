package com.santimattius.list.ui.screen.todolist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.santimattius.list.TodoListApp
import com.santimattius.list.data.TodoListRepository
import com.santimattius.list.domain.GetTodoItems
import com.santimattius.list.domain.TodoItem
import com.santimattius.list.ui.components.*

@Composable
fun TodoListScreen(
    todoViewModel: TodoViewModel = hiltViewModel(),
    onTodoItemClick: (TodoItem) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TodoAppBar(title = "Todo")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onTodoItemClick(TodoItem.empty())
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add todo list"
                )
            }
        }
    ) { paddingValues ->
        TodoListContent(
            todoViewModel = todoViewModel,
            modifier = Modifier.padding(paddingValues),
            onTodoItemClick = onTodoItemClick
        )
    }
}

@Composable
fun TodoListContent(
    todoViewModel: TodoViewModel,
    modifier: Modifier = Modifier,
    onTodoItemClick: (TodoItem) -> Unit = {},
) {
    with(todoViewModel.state) {
        when {
            isLoading -> LoadingIndicator(modifier = modifier)
            hasError -> ErrorView(
                message = "Message of Error",
                modifier = modifier
            )
            isEmpty -> EmptyView(modifier = modifier)
            else -> SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    todoViewModel.refresh()
                }) {
                LazyColumn(modifier = modifier) {
                    items(data, key = { it.id }) { item ->
                        TodoItemView(item, onTodoItemClick)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TodoListApp {
        TodoListScreen(TodoViewModel(GetTodoItems(TodoListRepository())))
    }
}