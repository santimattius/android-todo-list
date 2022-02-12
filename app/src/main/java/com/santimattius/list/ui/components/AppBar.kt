package com.santimattius.list.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun TodoAppBar(
    title: String,
    backAction: AppBarItem? = null,
    actions: List<AppBarItem> = emptyList(),
) {
    val navigationIcon: (@Composable () -> Unit) = {
        if (backAction != null) {
            AppBarIcon(
                icon = backAction.icon,
                contentDescription = backAction.contentDescription,
                action = backAction.action
            )
        }
    }
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
        },
        navigationIcon = backAction?.let { navigationIcon },
        actions = {
            for (action in actions) {
                AppBarIcon(
                    icon = action.icon,
                    contentDescription = action.contentDescription,
                    action = action.action
                )
            }
        }
    )
}

data class AppBarItem(
    val icon: ImageVector,
    val contentDescription: String = "Without description",
    val action: () -> Unit = {},
) {
    companion object {

        fun back(action: () -> Unit = {}) = AppBarItem(
            icon = Icons.Default.ArrowBack,
            contentDescription = "Back",
            action = action)
    }
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