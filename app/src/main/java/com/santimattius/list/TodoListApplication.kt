package com.santimattius.list

import android.app.Application
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.santimattius.list.ui.theme.TodoListTheme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoListApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 1
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
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