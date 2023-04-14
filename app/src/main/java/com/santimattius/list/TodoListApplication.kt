package com.santimattius.list

import android.app.Application
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.instabug.library.Instabug
import com.instabug.library.invocation.InstabugInvocationEvent
import com.santimattius.list.ui.theme.TodoListTheme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Instabug.Builder(this, BuildConfig.INSTABUG_KEY)
            .setInvocationEvents(
                InstabugInvocationEvent.SHAKE,
                InstabugInvocationEvent.FLOATING_BUTTON
            )
            .build()
    }
}

@Composable
fun TodoListApp(content: @Composable () -> Unit) {
    TodoListTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}