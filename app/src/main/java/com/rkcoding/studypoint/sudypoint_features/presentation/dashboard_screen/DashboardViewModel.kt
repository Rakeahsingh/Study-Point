package com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkcoding.studypoint.core.utils.ShowSnackBarEvent
import com.rkcoding.studypoint.core.utils.toHour
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SessionRepository
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SubjectRepository
import com.rkcoding.studypoint.sudypoint_features.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository
): ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = combine(
        _state,
        subjectRepository.getAllSubject(),
        subjectRepository.getTotalSubjectCount(),
        subjectRepository.getTotalGoalHour(),
        sessionRepository.getTotalSessionDuration()
    ){ state, subjects, subjectCount, goalHour, totalSessionDuration ->
        state.copy(
            subject = subjects,
            totalSubjectCountHour = subjectCount,
            totalGoalStudiesHour = goalHour,
            totalStudiesHour = totalSessionDuration.toHour()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = DashboardState()
    )

    val tasks: StateFlow<List<Task>> = taskRepository.getAllUpcomingTask()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val sessions: StateFlow<List<Session>> = sessionRepository.getAllSession()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _snakeBarEvent = MutableSharedFlow<ShowSnackBarEvent>()
    val snakeBar = _snakeBarEvent.asSharedFlow()


    fun onEvent(event: DashboardEvent){
        when(event){
            is DashboardEvent.DeleteSessionButtonClick -> {
                _state.update {
                    it.copy(
                        session = event.session
                    )
                }
            }
            DashboardEvent.DeleteSession -> TODO()
            is DashboardEvent.OnGoalHourChange -> {
                _state.update {
                    it.copy(
                        goalStudiesHour = event.hour
                    )
                }
            }
            is DashboardEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(
                        subjectCardColor = event.color
                    )
                }
            }
            is DashboardEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(
                        subjectName = event.name
                    )
                }
            }
            is DashboardEvent.OnTaskCompleteChange -> TODO()
            DashboardEvent.SaveSubject -> saveSubject()
        }
    }

    private fun saveSubject() {
        viewModelScope.launch {
            try {
                subjectRepository.upsertSubject(
                    Subject(
                        name = _state.value.subjectName,
                        goalHours = _state.value.goalStudiesHour.toFloatOrNull() ?: 1f,
                        color = _state.value.subjectCardColor.map { it.toArgb() }
                    )
                )
                _state.update {
                    it.copy(
                        subjectName = "",
                        goalStudiesHour = "",
                        subjectCardColor = Subject.subjectCardColor.random()
                    )
                }
                _snakeBarEvent.emit(
                    ShowSnackBarEvent.ShowSnakeBar("Save Subject Successfully")
                )
            }catch (e: Exception){
                _snakeBarEvent.emit(
                    ShowSnackBarEvent.ShowSnakeBar(
                        "Couldn't Save Subject. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }


}