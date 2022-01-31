package com.santimattius.list.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.santimattius.list.TodoListApp
import com.santimattius.list.ui.components.TodoItemView


@Composable
fun HomeScreen(todoViewModel: TodoViewModel) {
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
    ) {
        LazyColumn {
            items(10) {
                TodoItemView()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TodoListApp {
        HomeScreen(TodoViewModel())
    }
}