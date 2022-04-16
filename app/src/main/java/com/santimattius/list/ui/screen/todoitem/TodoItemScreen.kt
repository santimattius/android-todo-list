package com.santimattius.list.ui.screen.todoitem

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.santimattius.list.R
import com.santimattius.list.TodoListApp
import com.santimattius.list.domain.TodoItem
import com.santimattius.list.ui.components.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun TodoItemDetailScreen(
    todoItemViewModel: TodoItemViewModel = hiltViewModel(),
    onBackAction: () -> Unit = {},
) {

    if (todoItemViewModel.state.close) {
        Confirmation(action = onBackAction)
    } else {
        Scaffold(
            topBar = {
                TodoAppBar(
                    backAction = AppBarItem.back(onBackAction),
                    actions = listOf(
                        AppBarItem(
                            icon = Icons.Default.Save,
                            contentDescription = stringResource(R.string.text_desc_save_action)
                        ) {
                            todoItemViewModel.save()
                        }
                    )
                )
            }
        ) { innerPadding ->
            TodoItemContent(
                state = todoItemViewModel.state,
                modifier = Modifier.padding(innerPadding),
                onTodoItemChange = todoItemViewModel::update
            )
        }
    }
}

@Composable
private fun Confirmation(delay: Long = 200L, action: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confirmation))
        val progress by animateLottieCompositionAsState(composition)
        LottieAnimation(
            composition,
            progress,
        )
        if (progress == 1.0f) {
            LaunchedEffect(key1 = true) {
                delay(delay)
                action()
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun TodoItemContent(
    state: TodoItemScreenState,
    modifier: Modifier = Modifier,
    onTodoItemChange: (TodoItem) -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Box(modifier = modifier.fillMaxSize()) {
        with(state) {
            when {
                isLoading -> LoadingIndicator()
                withError -> ErrorView(message = stringResource(R.string.text_msg_error_todo_detail))
                else -> {
                    if (isEmpty) {
                        val message = stringResource(R.string.text_msg_attr_required)
                        scope.launch {
                            snackBarHostState.showSnackbar(message)
                        }
                    }
                    TodoForm(
                        todoItem = todoItem,
                        onTodoItemChange = onTodoItemChange
                    )
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            SnackbarHost(hostState = snackBarHostState)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun TodoForm(
    todoItem: TodoItem,
    modifier: Modifier = Modifier,
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

@ExperimentalComposeUiApi
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