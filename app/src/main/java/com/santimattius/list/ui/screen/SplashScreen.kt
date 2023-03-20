package com.santimattius.list.ui.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.santimattius.list.R

@Composable
fun SplashRoute(navigate: () -> Unit) {
    SplashScreen(navigate = navigate)
}

@Composable
private fun SplashScreen(navigate: () -> Unit) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(4f)
                        .getInterpolation(it)
                })
        )
        FirebaseRemoteConfig.getInstance().fetchAndActivate().addOnCompleteListener {
            navigate()
        }

    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash),
                contentDescription = stringResource(id = R.string.text_desc_confirmation),
                modifier = Modifier.scale(scale.value)
            )
            if (!scale.isRunning) {
                CircularProgressIndicator()
            }
        }
    }
}