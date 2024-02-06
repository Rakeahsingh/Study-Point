package com.rkcoding.studypoint.sudypoint_features.presentation.task_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkcoding.studypoint.core.utils.Priority
import com.rkcoding.studypoint.core.utils.ShowSnackBarEvent
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task
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
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val subjectRepository: SubjectRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val subjectId = savedStateHandle.get<Int>("subjectId") ?: 0
    val taskId = savedStateHandle.get<Int>("taskId") ?: 0

    private val _state = MutableStateFlow(TaskState())
    val state = combine(
        _state,
        subjectRepository.getAllSubject()
    ){ state, subjects ->
        state.copy(
            subjects = subjects
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = TaskState()
    )

    private val _snackBar = Channel<ShowSnackBarEvent>()
    val snackBar = _snackBar.receiveAsFlow()

    init {
        fetchTask()
        fetchSubject()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: TaskEvent){
        when(event){

            TaskEvent.OnDeleteTask -> deleteTask()

            is TaskEvent.OnDescriptionChange -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is TaskEvent.OnDueDateChange -> {
                _state.update {
                    it.copy(
                        dueDate = event.millis
                    )
                }
            }

            TaskEvent.OnIsCompleteChange -> {
                _state.update {
                    it.copy(
                        isTaskComplete = !_state.value.isTaskComplete
                    )
                }
            }

            is TaskEvent.OnPriorityChange -> {
                _state.update {
                    it.copy(
                        priority = event.priority
                    )
                }
            }

            is TaskEvent.OnRelatedSubjectSelect -> {
                _state.update {
                    it.copy(
                        relatedToSubject = event.subject.name,
                        subjectId = event.subject.subjectId
                    )
                }
            }

            TaskEvent.OnSaveTask -> saveTask()

            is TaskEvent.OnTitleChange -> {
                _state.update {
                    it.copy(
                        title = event.name
                    )
                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveTask() {
        viewModelScope.launch {
            try {
                if (_state.value.relatedToSubject == null || _state.value.subjectId == null){
                    _snackBar.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        message = "Please select subject related to Task",
                        duration = SnackbarDuration.Long
                    )
                )
                    return@launch
                }
                taskRepository.upsertTask(
                    Task(
                        title = _state.value.title,
                        description = _state.value.description,
                        dueDate = _state.value.dueDate ?: Instant.now().toEpochMilli(),
                        priority = _state.value.priority.value ,
                        relatedToSubject = _state.value.relatedToSubject!!,
                        isCompleted = _state.value.isTaskComplete,
                        taskSubjectId = _state.value.subjectId!!,
                        taskId = _state.value.currentTaskId
                    )
                )

                _snackBar.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        message = "Task Saved Successfully",
                        duration = SnackbarDuration.Long
                    )
                )

                _snackBar.send(
                    ShowSnackBarEvent.NavigateUp
                )

            }catch (e: Exception){
                _snackBar.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        message = "Task Couldn't be Save",
                        duration = SnackbarDuration.Long
                    )
                )
            }

        }
    }


    private fun fetchTask(){
        viewModelScope.launch {
            taskRepository.getTaskById(taskId)?.let { task ->
                _state.update {
                    it.copy(
                        title = task.title,
                        description = task.description,
                        dueDate = task.dueDate,
                        priority = Priority.fromInt(task.priority),
                        relatedToSubject = task.relatedToSubject,
                        subjectId = task.taskSubjectId,
                        isTaskComplete = task.isCompleted,
                        currentTaskId = task.taskId
                    )
                }
            }
        }
    }

    private fun deleteTask() {
        viewModelScope.launch {
            try {
                val currentTaskId = _state.value.currentTaskId
                if (currentTaskId != null){
                    withContext(Dispatchers.IO){
                        taskRepository.deleteTask(taskId = currentTaskId)
                    }
                    _snackBar.send(
                        ShowSnackBarEvent.ShowSnakeBar(
                            "Task Deleted Successfully",
                            SnackbarDuration.Short
                        )
                    )
                    _snackBar.send(
                        ShowSnackBarEvent.NavigateUp
                    )
                }else{
                    _snackBar.send(
                        ShowSnackBarEvent.ShowSnakeBar(
                            "No Task to Delete",
                            SnackbarDuration.Short
                        )
                    )
                }

            }catch (e: Exception){
                _snackBar.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        "Couldn't Delete Task. ${e.message}",
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
                       subjectId = subject.subjectId,
                        relatedToSubject = subject.name
                    )
                }
            }
        }
    }

}