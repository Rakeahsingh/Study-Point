package com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimerSection (
    modifier: Modifier = Modifier,
    hour: String,
    minute: String,
    second: String
) {
    Box(
        modifier = modifier
            .size(200.dp)
            .border(5.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape),
        contentAlignment = Alignment.Center
    ){

        Row {
            AnimatedContent(
                targetState = hour,
                transitionSpec = { timerTextAnimation() },
                label = "hour Animation"
            ) { hour ->
                Text(
                    text = "$hour:",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            AnimatedContent(
                targetState = minute,
                transitionSpec = { timerTextAnimation() },
                label = "hour Animation"
            ) { minute ->
                Text(
                    text = "$minute:",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            AnimatedContent(
                targetState = second,
                transitionSpec = { timerTextAnimation() },
                label = "hour Animation"
            ) { second ->
                Text(
                    text = second,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

    }
}


private fun timerTextAnimation(duration: Int = 600): ContentTransform{
    return slideInVertically(animationSpec = tween(duration)){ fullHeight -> fullHeight } +
            fadeIn(animationSpec = tween(duration)) togetherWith
            slideOutVertically(animationSpec = tween(duration)){ fullHeight -> -fullHeight } +
            fadeOut(animationSpec = tween(duration))
}