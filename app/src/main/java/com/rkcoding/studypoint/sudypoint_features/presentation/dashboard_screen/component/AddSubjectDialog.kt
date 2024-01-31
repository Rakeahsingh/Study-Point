package com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject

@Composable
fun AddSubjectDialog(
    isDialogOpen: Boolean,
    selectedColor: List<Color>,
    subjectName: String,
    goalHour: String,
    onSubjectNameChange: (String) -> Unit,
    onGoalHourChange: (String) -> Unit,
    onColorChange: (List<Color>) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {

    var subjectNameError by remember {
        mutableStateOf<String?>(null)
    }
    var goalHourError by remember {
        mutableStateOf<String?>(null)
    }

    subjectNameError = when{
        subjectName.isBlank() -> "Please Enter the Subject Name"
        subjectName.length < 2 -> "Subject Name is too Short"
        subjectName.length > 20 -> "Subject name is too Long"
        else -> null
    }
    goalHourError = when{
        goalHour.isBlank() -> "Please Enter goal study hour"
        goalHour.toFloatOrNull() == null -> "Invalid Number"
        goalHour.toFloat() < 1f -> "Please set at least 1 hour"
        goalHour.toFloat() > 1000f -> "Please set maximum of 100 hour"
        else -> null
    }

    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            confirmButton = {
                TextButton(
                    onClick = { onConfirmButtonClick() },
                    enabled = subjectNameError == null && goalHourError == null
                ) {
                    Text(text = "Save")
                }
            },
            title = {
                Text(text = "Add/Update Subjects")
            },
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Subject.subjectCardColor.forEach { colors ->
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .border(
                                        1.dp,
                                        if (colors == selectedColor) Color.Black else Color.Transparent,
                                        CircleShape
                                    )
                                    .background(brush = Brush.verticalGradient(colors))
                                    .clickable {
                                        onColorChange(colors)
                                    }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = subjectName,
                        onValueChange = onSubjectNameChange,
                        label = { Text(text = "Subject Name")},
                        singleLine = true,
                        isError = subjectNameError != null && subjectName.isNotBlank(),
                        supportingText = { Text(text = subjectNameError.orEmpty())}
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = goalHour,
                        onValueChange = onGoalHourChange,
                        label = { Text(text = "Goal Study Hours")},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = goalHourError != null && goalHour.isNotBlank(),
                        supportingText = { Text(text = goalHourError.orEmpty())}
                    )

                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDismissRequest() }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }

}


