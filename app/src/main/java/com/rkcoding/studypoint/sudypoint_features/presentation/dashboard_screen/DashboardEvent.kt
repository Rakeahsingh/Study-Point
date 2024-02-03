package com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen

import androidx.compose.ui.graphics.Color
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task

sealed class DashboardEvent {

    data object SaveSubject: DashboardEvent()
    data object DeleteSession: DashboardEvent()
    data class DeleteSessionButtonClick(val session: Session): DashboardEvent()
    data class OnTaskCompleteChange(val task: Task): DashboardEvent()
    data class OnSubjectCardColorChange(val color: List<Color>): DashboardEvent()
    data class OnSubjectNameChange(val name: String): DashboardEvent()
    data class OnGoalHourChange(val hour: String): DashboardEvent()

}