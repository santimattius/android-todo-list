package com.santimattius.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.santimattius.list.ui.screen.HomeScreen
import com.santimattius.list.ui.screen.TodoViewModel

class MainActivity : ComponentActivity() {

    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListApp {
                HomeScreen(todoViewModel)
            }
        }
    }
}
