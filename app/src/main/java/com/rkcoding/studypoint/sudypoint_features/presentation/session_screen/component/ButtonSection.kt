package com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rkcoding.studypoint.ui.theme.DarkBlue
import com.rkcoding.studypoint.ui.theme.DarkRed
import com.rkcoding.studypoint.ui.theme.Orange

@Composable
fun ButtonSection(
    modifier: Modifier,
    statButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit,
    finishButtonClick: () -> Unit,
    timerState: TimerState,
    second: String
) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            onClick = cancelButtonClick,
            enabled = second != "00" && timerState != TimerState.STARTED,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = DarkBlue
            )
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                text = "Cancel"
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Button(
            onClick = statButtonClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = when(timerState){
                    TimerState.STARTED -> DarkRed
                    TimerState.STOPPED -> Orange
                    else -> DarkBlue
                }
            )
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                text = when(timerState){
                    TimerState.STARTED -> "Stop"
                    TimerState.STOPPED -> "Resume"
                    else -> "Start"
                }
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Button(
            onClick = finishButtonClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = DarkBlue
            ),
            enabled = second != "00" && timerState != TimerState.STARTED
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                text = "Finish"
            )
        }

    }

}