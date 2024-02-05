package com.rkcoding.studypoint.sudypoint_features.presentation.task_screen

import com.rkcoding.studypoint.core.utils.Priority
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject

data class TaskState(
    val title: String = "",
    val description: String = "",
    val dueDate: Long? = null,
    val isTaskComplete: Boolean = false,
    val priority: Priority = Priority.LOW,
    val relatedToSubject: String? = null,
    val subjects: List<Subject> = emptyList(),
    val subjectId: Int? = null,
    val currentTaskId: Int? = null
)
