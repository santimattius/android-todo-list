package com.santimattius.list.ui.screen.todoitem

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.santimattius.list.R
import com.santimattius.list.TodoListApp
import com.santimattius.list.domain.TodoItem
import com.santimattius.list.ui.components.*

@Composable
fun TodoItemDetailScreen(
    todoItemViewModel: TodoItemViewModel = hiltViewModel(),
    onBackAction: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TodoAppBar(
                title = "",
                backAction = AppBarItem.back(onBackAction),
                actions = listOf(
                    AppBarItem(icon = Icons.Default.Save) {
                        todoItemViewModel.save()
                        onBackAction()
                    }
                )
            )
        }
    ) { innerPadding ->
        Surface(
            color = Color.White,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            TodoItemContent(
                state = todoItemViewModel.state,
                onTodoItemChange = todoItemViewModel::update
            )
        }
    }
}

@Composable
private fun TodoItemContent(
    state: TodoItemScreenState,
    modifier: Modifier = Modifier,
    onTodoItemChange: (TodoItem) -> Unit = {},
) {
    with(state) {
        when {
            isLoading -> LoadingIndicator()
            withError -> ErrorView(message = "Todo Item error")
            else -> TodoForm(
                todoItem = todoItem,
                modifier = modifier,
                onTodoItemChange = onTodoItemChange
            )
        }
    }

}

@Composable
private fun TodoForm(
    todoItem: TodoItem,
    modifier: Modifier,
    onTodoItemChange: (TodoItem) -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        TodoInputText(
            text = todoItem.title,
            label = stringResource(R.string.text_input_label_title),
            onTextChange = { newTitleValue ->
                onTodoItemChange(todoItem.copy(title = newTitleValue))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        TodoInputText(
            text = todoItem.description,
            label = stringResource(R.string.text_input_label_description),
            onTextChange = { newDescriptionValue ->
                onTodoItemChange(todoItem.copy(description = newDescriptionValue))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TodoItemDescriptionPreview() {
    TodoListApp(
        content = {
            TodoItemContent(
                state = TodoItemScreenState(todoItem = TodoItem.empty()),
                onTodoItemChange = { }
            )
        }
    )
}