package com.santimattius.list.ui.screen.todoitem

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
fun TodoItemDetailRoute(
    todoItemViewModel: TodoItemViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onBackAction: () -> Unit = {},
) {

    if (todoItemViewModel.state.close) {
        Confirmation(action = onBackAction)
    } else {
        TodoItemScreen(
            state = todoItemViewModel.state,
            scaffoldState = scaffoldState,
            onBackAction = onBackAction,
            onSaveAction = todoItemViewModel::save,
            onUpdateAction = todoItemViewModel::update,
            onErrorDismissState = todoItemViewModel::errorDismiss
        )
    }
}

@ExperimentalComposeUiApi
@Composable
private fun TodoItemScreen(
    state: TodoItemScreenState,
    scaffoldState: ScaffoldState,
    onBackAction: () -> Unit = {},
    onSaveAction: () -> Unit = {},
    onUpdateAction: (TodoItem) -> Unit = {},
    onErrorDismissState: () -> Unit = {},
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(hostState = it) },
        topBar = {
            TodoAppBar(
                backAction = AppBarItem.back(onBackAction),
                actions = listOf(
                    AppBarItem(
                        icon = Icons.Default.Check,
                        contentDescription = stringResource(R.string.text_desc_save_action),
                        action = onSaveAction
                    )
                )
            )
        }
    ) { innerPadding ->
        TodoItemContent(
            state = state,
            scaffoldState = scaffoldState,
            modifier = Modifier.padding(innerPadding),
            onTodoItemChange = onUpdateAction,
            onErrorDismissState = onErrorDismissState
        )
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
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    onTodoItemChange: (TodoItem) -> Unit = {},
    onErrorDismissState: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    with(state) {
        when {
            isLoading -> LoadingIndicator(modifier = modifier)
            withError -> ErrorView(
                message = stringResource(R.string.text_msg_error_todo_detail),
                modifier = modifier
            )
            else -> {
                if (state.isEmpty) {
                    val errorMessageText = stringResource(R.string.text_msg_attr_required)
                    coroutineScope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = errorMessageText,
                        )
                        if (result == SnackbarResult.Dismissed) {
                            // Once the message is displayed and dismissed, notify the ViewModel
                            onErrorDismissState()
                        }
                    }
                }
                TodoForm(
                    todoItem = todoItem,
                    modifier = modifier,
                    onTodoItemChange = onTodoItemChange
                )
            }
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
                scaffoldState = rememberScaffoldState(),
                onTodoItemChange = { }
            )
        }
    )
}