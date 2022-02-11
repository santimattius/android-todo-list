package com.santimattius.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.santimattius.list.ui.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListApp {
                Navigation()
            }
        }
    }
}
