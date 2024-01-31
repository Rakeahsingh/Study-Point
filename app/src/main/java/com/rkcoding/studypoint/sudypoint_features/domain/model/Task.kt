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


enum class Priority(val title: String, val color: Color, val value: Int){
    LOW("Low", CustomGreen, 0),
    MEDIUM("Medium", Orange, 1),
    HIGH("High", Color.Red, 2);

    companion object{
        fun fromInt(value: Int) = entries.firstOrNull() { it.value == value } ?: MEDIUM
    }

}
