package com.santimattius.list.ui.screen.todoitem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.santimattius.list.R
import com.santimattius.list.domain.TodoItem
import com.santimattius.list.domain.isEmpty

@Composable
fun TodoItemDialogRoute(
    viewModel: TodoItemViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
) {
    val currentState = viewModel.state
    if (currentState.close) {
        onDismiss()
    } else {
        TodoItemDialog(
            value = currentState.todoItem,
            actionType = currentState.actionType,
            onUpdate = viewModel::update,
            onAction = viewModel::save,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun TodoItemDialog(
    value: TodoItem,
    actionType: ActionType,
    onUpdate: (TodoItem) -> Unit,
    onAction: () -> Unit,
    onDismiss: () -> Unit,
) {

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Note",
                        style = MaterialTheme.typography.h6
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = colorResource(android.R.color.darker_gray),
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .clickable { onDismiss() }
                    )
                }

                TextInput(
                    value = value.title,
                    placeHolder = stringResource(R.string.text_input_label_title),
                    onValueChange = { newTitleValue ->
                        onUpdate(value.copy(title = newTitleValue))
                    },
                )

                TextInput(
                    value = value.description,
                    placeHolder = stringResource(R.string.text_input_label_description),
                    onValueChange = { newDescriptionValue ->
                        onUpdate(value.copy(description = newDescriptionValue))
                    }
                )

                Button(
                    enabled = !value.isEmpty(),
                    onClick = {
                        onAction()
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 8.dp)
                ) {
                    Text(text = if (actionType == ActionType.EDIT) "Edit" else "Create")
                }
            }
        }
    }
}

@Composable
private fun TextInput(
    value: String,
    placeHolder: String = "",
    onValueChange: (String) -> Unit,
    error: String = "",
) {
    val errorColor = if (error.isEmpty()) R.color.purple_200 else R.color.purple_500

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = errorColor)
                ),
                shape = RoundedCornerShape(8.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = { Text(text = placeHolder) },
        value = value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = onValueChange
    )
}