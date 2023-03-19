package com.santimattius.list.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Message
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.google.firebase.appdistribution.ktx.appDistribution
import com.google.firebase.ktx.Firebase
import com.santimattius.list.BuildConfig
import com.santimattius.list.R


@Composable
fun TodoAppBar(
    title: String = "",
    backAction: AppBarItem? = null,
    actions: List<AppBarItem> = emptyList(),
) {
    val appbarActions = if (BuildConfig.DEBUG) {
        actions + firebaseAction()
    } else {
        actions
    }
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
            for (action in appbarActions) {
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
    val contentDescription: String,
    val action: () -> Unit = {},
) {
    companion object {

        fun back(action: () -> Unit = {}) = AppBarItem(
            icon = Icons.Default.Close,
            contentDescription = "Back",
            action = action
        )
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


fun firebaseAction(): AppBarItem {
    return AppBarItem(
        icon = Icons.Default.Message,
        contentDescription = "Feedback",
        action = {
            Firebase.appDistribution.startFeedback(R.string.feedback_message)
        }
    )
}