package com.santimattius.list.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.santimattius.list.TodoListApp

@Composable
fun TodoItemDetailScreen() {
    Scaffold(
        topBar = {
            AppBar()
        }
    ) { innerPadding ->
        Surface(
            color = Color.White,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            TodoItemContent()
        }
    }
}

@Composable
private fun TodoItemContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var title by remember { mutableStateOf(TextFieldValue("")) }
        var description by remember { mutableStateOf(TextFieldValue("")) }
        InputText(
            title = title,
            label = "Title",
            onChange = {
                title = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        InputText(
            title = description,
            label = "Description",
            onChange = {
                description = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.5f)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun AppBar() {
    TopAppBar(
        navigationIcon = {
            AppBarIcon(
                icon = Icons.Default.ArrowBack,
                contentDescription = "Back"
            ) {
                //TODO: action of icon
            }
        },
        title = {
            Text(
                text = "Top bar",
                style = MaterialTheme.typography.h6
            )
        },
        actions = {
            AppBarIcon(
                icon = Icons.Default.Send,
                contentDescription = "Save"
            ) {
                //TODO: action of icon
            }
        }
    )
}

@Composable
private fun AppBarIcon(
    icon: ImageVector,
    contentDescription: String,
    action: () -> Unit,
) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clickable(onClick = action)
    )
}

@Composable
private fun InputText(
    title: TextFieldValue,
    label: String,
    onChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = title,
        label = {
            Text(text = label)
        },
        onValueChange = onChange,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TodoItemDescriptionPreview() {
    TodoListApp(
        content = {
            TodoItemDetailScreen()
        }
    )
}