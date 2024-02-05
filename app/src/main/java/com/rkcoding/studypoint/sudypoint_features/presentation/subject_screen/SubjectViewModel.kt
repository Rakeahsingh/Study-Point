package com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkcoding.studypoint.core.utils.ShowSnackBarEvent
import com.rkcoding.studypoint.core.utils.toHour
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SessionRepository
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SubjectRepository
import com.rkcoding.studypoint.sudypoint_features.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _snackBarEvent = Channel<ShowSnackBarEvent>()
    val snackBarEvent = _snackBarEvent.receiveAsFlow()

    init {
        fetchSubject()
    }


    fun onEvent(event: SubjectEvent){
        when(event){
            SubjectEvent.DeleteSession -> TODO()

            SubjectEvent.DeleteSubject -> deleteSubject()

            is SubjectEvent.OnGoalHourChange -> {
                _state.update {
                    it.copy(
                        goalStudiesHour = event.hour
                    )
                }
            }

            is SubjectEvent.OnSessionDeleteButtonClick -> TODO()

            is SubjectEvent.OnSubjectColorChange -> {
                _state.update {
                    it.copy(
                        subjectCardColor = event.color
                    )
                }
            }

            is SubjectEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(
                        subjectName = event.name
                    )
                }
            }
            is SubjectEvent.OnTaskCompleteChange -> TODO()

            SubjectEvent.UpdateSubject -> updateSubject()

            SubjectEvent.UpdateProgress -> {
                val goalStudiedHour = _state.value.goalStudiesHour.toFloatOrNull() ?: 1f
                _state.update {
                    it.copy(
                        progress = (_state.value.progress / goalStudiedHour).coerceIn(0f, 1f)
                    )
                }
            }

        }
    }

    private fun deleteSubject() {
        viewModelScope.launch {
            try {
                val currentSubjectId = _state.value.currentSubjectId
                if (currentSubjectId != null){
                    withContext(Dispatchers.IO){
                        subjectRepository.deleteSubject(subjectId = currentSubjectId)
                    }
                    _snackBarEvent.send(
                        ShowSnackBarEvent.ShowSnakeBar(
                            "Subject Deleted Successfully",
                            SnackbarDuration.Short
                        )
                    )
                    _snackBarEvent.send(
                        ShowSnackBarEvent.NavigateUp
                    )
                }else{
                    _snackBarEvent.send(
                        ShowSnackBarEvent.ShowSnakeBar(
                            "No Subject to Delete",
                            SnackbarDuration.Short
                        )
                    )
                }

            }catch (e: Exception){
                _snackBarEvent.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        "Couldn't Delete Subject. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }

        }
    }

    private fun updateSubject() {
        viewModelScope.launch {
            try {
                subjectRepository.upsertSubject(
                    Subject(
                        subjectId = _state.value.currentSubjectId,
                        name = _state.value.subjectName,
                        goalHours = _state.value.goalStudiesHour.toFloatOrNull() ?: 1f,
                        color = _state.value.subjectCardColor.map { it.toArgb() }
                    )
                )

                _snackBarEvent.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        "Subject Updated Successfully",
                        SnackbarDuration.Short
                    )
                )

                _state.update {
                    it.copy(
                        subjectName = "",
                        goalStudiesHour = "",
                        subjectCardColor = Subject.subjectCardColor.random()
                    )
                }

            }catch (e: Exception){
                _snackBarEvent.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        "Couldn't Update Subject. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun fetchSubject(){
        viewModelScope.launch {
            subjectRepository.getSubjectById(subjectId)?.let { subject ->
                _state.update {
                    it.copy(
                       subjectName = subject.name,
                        goalStudiesHour = subject.goalHours.toString(),
                        subjectCardColor = subject.color.map { color -> Color(color) },
                        currentSubjectId = subject.subjectId
                    )
                }
            }
        }
    }

}