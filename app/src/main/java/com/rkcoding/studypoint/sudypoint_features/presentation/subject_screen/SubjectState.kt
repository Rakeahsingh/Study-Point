package com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen

import androidx.compose.ui.graphics.Color
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task

data class SubjectState(
    val currentSubjectId: Int? = null,
    val subjectName: String = "",
    val goalStudiesHour: String = "",
    val studiedHour: Float = 0f,
    val progress: Float = 0f,
    val subjectCardColor: List<Color> = Subject.subjectCardColor.random(),
    val upcomingTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList(),
    val recentStudiesSession: List<Session> = emptyList(),
    val session: Session? = null,
    val isLoading: Boolean = false
)
