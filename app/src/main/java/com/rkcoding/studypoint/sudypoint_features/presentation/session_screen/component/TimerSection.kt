package com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(200.dp)
            .border(5.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "00:05:59",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}