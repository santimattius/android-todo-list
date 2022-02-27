package com.santimattius.list.ui.screen.todolist

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.santimattius.list.TodoListApp
import com.santimattius.list.data.TodoListRepository
import com.santimattius.list.domain.GetTodoItems
import com.santimattius.list.domain.TodoItem
import com.santimattius.list.ui.components.*

@ExperimentalMaterialApi
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

@ExperimentalMaterialApi
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
                        TodoListContentItem(item, onTodoItemClick)
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun TodoListContentItem(
    item: TodoItem,
    onTodoItemClick: (TodoItem) -> Unit,
) {
    var unread by remember { mutableStateOf(false) }
    val dismissState = rememberDismissState(
        confirmStateChange = {
            //TODO: remove item
            if (it == DismissValue.DismissedToEnd) unread = !unread
            it != DismissValue.DismissedToEnd
        }
    )
    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = 4.dp),
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
        },
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> LightGray
                    DismissValue.DismissedToEnd -> Green
                    DismissValue.DismissedToStart -> Red
                }
            )
            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val icon = when (direction) {
                DismissDirection.StartToEnd -> Icons.Default.Done
                DismissDirection.EndToStart -> Icons.Default.Delete
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    contentDescription = "Localized description",
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = {
            TodoItemCard(
                item = item,
                elevation = animateDpAsState(
                    if (dismissState.dismissDirection != null) 4.dp else 0.dp,
                ).value,
            ) {
                onTodoItemClick(it)
            }
        }
    )


}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TodoListApp {
        TodoListScreen(TodoViewModel(GetTodoItems(TodoListRepository())))
    }
}