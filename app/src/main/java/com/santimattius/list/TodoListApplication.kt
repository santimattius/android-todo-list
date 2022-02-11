package com.santimattius.list

import android.app.Application
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.santimattius.list.ui.theme.TodoListTheme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoListApplication : Application()

@Composable
fun TodoListApp(content: @Composable () -> Unit) {
    TodoListTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}