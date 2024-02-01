package com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.CountCard
import com.rkcoding.studypoint.ui.theme.CustomGreen

@Composable
fun CountCardSection(
    modifier: Modifier = Modifier,
    goalHour: String,
    studyHour: String,
    progress: Float
) {

    val percentProgress = remember(progress) {
        (progress * 100).toInt().coerceIn(0, 100)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headlineText = "Goal Study Hour",
            count = goalHour
        )

        Spacer(modifier = Modifier.width(16.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headlineText = "Study Hour",
            count = studyHour
        )
        Spacer(modifier = Modifier.width(16.dp))
        
        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.surfaceVariant,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                progress = 1f
            )

            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = CustomGreen,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                progress = progress
            )

            Text(
                text = "$percentProgress%"
            )
        }
    }

}

