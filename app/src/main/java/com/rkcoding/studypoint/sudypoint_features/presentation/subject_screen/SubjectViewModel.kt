package com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkcoding.studypoint.core.utils.toHour
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SessionRepository
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SubjectRepository
import com.rkcoding.studypoint.sudypoint_features.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val taskRepository: TaskRepository,
    private val sessionRepository: SessionRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val subjectId = savedStateHandle.get<Int>("subjectId") ?: 0

    private val _state = MutableStateFlow(SubjectState())
    val state = combine(
        _state,
        taskRepository.getUpcomingTaskForSubjectId(subjectId),
        taskRepository.getCompletedTaskForSubjectId(subjectId),
        sessionRepository.getRecentTenSessionForSubject(subjectId),
        sessionRepository.getTotalSessionDurationBySubjectId(subjectId)
    ){ state, upcomingTask, completedTask, recentSessions, totalSessionDuration ->
        state.copy(
            upcomingTasks = upcomingTask,
            completedTasks = completedTask,
            recentStudiesSession = recentSessions,
            studiedHour = totalSessionDuration.toHour()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = SubjectState()
    )


    fun onEvent(event: SubjectEvent){
        when(event){
            SubjectEvent.DeleteSession -> TODO()
            SubjectEvent.DeleteSubject -> TODO()
            is SubjectEvent.OnGoalHourChange -> TODO()
            is SubjectEvent.OnSessionDeleteButtonClick -> TODO()
            is SubjectEvent.OnSubjectColorChange -> TODO()
            is SubjectEvent.OnSubjectNameChange -> TODO()
            is SubjectEvent.OnTaskCompleteChange -> TODO()
            SubjectEvent.UpdateSubject -> TODO()
        }
    }

}