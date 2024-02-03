package com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen

import androidx.compose.ui.graphics.Color
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject

data class DashboardState(
    val totalSubjectCountHour: Int = 0,
    val totalStudiesHour: Float = 0f,
    val totalGoalStudiesHour: Float = 0f,
    val subject: List<Subject> = emptyList(),
    val subjectName: String = "",
    val goalStudiesHour: String = "",
    val subjectCardColor: List<Color> = Subject.subjectCardColor.random(),
    val session: Session? = null
)
