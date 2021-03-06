package com.santimattius.list.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.santimattius.list.TodoListApp
import com.santimattius.list.domain.TodoItem
import java.util.*

@Composable
fun TodoItemCard(
    item: TodoItem,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    onClick: (TodoItem) -> Unit = {},
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxSize()
            .wrapContentHeight(align = Alignment.Top)
            .clickable { onClick(item) },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = elevation
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = item.title
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = item.date.asString(),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TodoItemViewPreview() {
    TodoListApp {
        TodoItemCard(
            TodoItem(
                id = UUID.randomUUID(),
                title = "Title",
                description = "Description",
            ),
        )
    }
}