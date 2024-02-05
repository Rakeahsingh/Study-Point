package com.rkcoding.studypoint.sudypoint_features.presentation.task_screen

import com.rkcoding.studypoint.core.utils.Priority
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task

sealed class TaskEvent {

    data class OnTitleChange(val name: String): TaskEvent()
    data class OnDescriptionChange(val description: String): TaskEvent()
    data class OnDueDateChange(val millis: Long?): TaskEvent()
    data class OnPriorityChange(val priority: Priority): TaskEvent()
    data class OnRelatedSubjectSelect(val subject: Subject): TaskEvent()
    data object OnIsCompleteChange: TaskEvent()
    data object OnSaveTask: TaskEvent()
    data object OnDeleteTask: TaskEvent()

}