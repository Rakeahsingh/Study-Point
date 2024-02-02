package com.rkcoding.studypoint.sudypoint_features.domain.model

import androidx.compose.ui.graphics.Color
import com.rkcoding.studypoint.ui.theme.CustomGreen
import com.rkcoding.studypoint.ui.theme.Orange

data class Task(
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val relatedToSubject: String,
    val isCompleted: Boolean,
    val taskSubjectId: Int,
    val taskId: Int
)



