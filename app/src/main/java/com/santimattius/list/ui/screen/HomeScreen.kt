package com.santimattius.list.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.santimattius.list.TodoListApp
import com.santimattius.list.data.TodoListRepository
import com.santimattius.list.domain.GetTodoItems
import com.santimattius.list.ui.components.TodoItemView


@Composable
fun HomeScreen(
    todoViewModel: TodoViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Top bar") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { }
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
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun TodoListContent(
    todoViewModel: TodoViewModel,
    modifier: Modifier = Modifier,
) {
    with(todoViewModel.state) {
        when {
            isLoading -> LoadingIndicator(modifier = modifier)
            hasError -> ErrorView(
                message = "Message of Error",
                modifier = modifier
            )
            isEmpty -> EmptyView(modifier = modifier)
            else -> LazyColumn(modifier = modifier) {
                items(data, key = { it.id }) { item ->
                    TodoItemView(item)
                }
            }
        }
    }
}

@Composable
fun EmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Load icon")
        Text(text = "Create new todo item")
    }
}

@Composable
fun ErrorView(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TodoListApp {
        HomeScreen(TodoViewModel(GetTodoItems(TodoListRepository())))
    }
}