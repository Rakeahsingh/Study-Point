package com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen

import androidx.compose.ui.graphics.Color
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task

sealed class SubjectEvent {

    data object UpdateSubject: SubjectEvent()
    data object DeleteSubject: SubjectEvent()
    data object DeleteSession: SubjectEvent()
    data object UpdateProgress: SubjectEvent()
    data class OnTaskCompleteChange(val task: Task): SubjectEvent()
    data class OnSubjectColorChange(val color: List<Color>): SubjectEvent()
    data class OnSubjectNameChange(val name: String): SubjectEvent()
    data class OnGoalHourChange(val hour: String): SubjectEvent()
    data class OnSessionDeleteButtonClick(val session: Session): SubjectEvent()

}