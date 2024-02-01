package com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    isDialogOpen: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    bodyText: String,
    title: String
) {



    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            confirmButton = {
                TextButton(
                    onClick = { onConfirmButtonClick() }
                ) {
                    Text(text = "Delete")
                }
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(
                    text = bodyText
                )
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

