package com.rkcoding.studypoint.sudypoint_features.presentation.session_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkcoding.studypoint.core.utils.ShowSnackBarEvent
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SessionRepository
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val sessionRepository: SessionRepository
): ViewModel() {

    private val _state = MutableStateFlow(SessionState())
    val state = combine(
        _state,
        subjectRepository.getAllSubject(),
        sessionRepository.getAllSession()
    ){ state, subjects, sessions ->
        state.copy(
            subjects = subjects,
            sessions = sessions
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = SessionState()
    )

    private val _snackBar = Channel<ShowSnackBarEvent>()
    val snackBar = _snackBar.receiveAsFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: SessionEvent){
        when(event){

            SessionEvent.NotifyToUpdateSubject -> notifyToUpdateSubject()

            SessionEvent.DeleteSession -> deleteSession()

            is SessionEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(
                        session = event.session
                    )
                }
            }

            is SessionEvent.OnRelatedSubjectChange -> {
                _state.update {
                    it.copy(
                        relatedToSubject = event.subject.name,
                        subjectId = event.subject.subjectId
                    )
                }
            }

            is SessionEvent.SaveSession -> insertSession(event.duration)

            is SessionEvent.UpdateSubjectIdAndRelatedSubject -> {
                _state.update {
                    it.copy(
                        relatedToSubject = event.relatedToSubject,
                        subjectId = event.subjectId
                    )
                }
            }

        }
    }

    private fun notifyToUpdateSubject() {
        viewModelScope.launch {
            if (_state.value.subjectId == null || _state.value.relatedToSubject == null){
                _snackBar.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        "Please Select Subject related too the Session",
                        SnackbarDuration.Short
                    )
                )
            }
        }
    }

    private fun deleteSession(){
        viewModelScope.launch {
            try {
                _state.value.session?.let {
                    sessionRepository.deleteSession(it)
                }
                _snackBar.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        "Session Deleted Successfully",
                        SnackbarDuration.Short
                    )
                )
            }catch (e: Exception){
                _snackBar.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        "Couldn't Delete Session. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertSession(duration: Long) {
        viewModelScope.launch {
            if (duration < 30){
                _snackBar.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        "Single Session can't be less than 30 seconds",
                        SnackbarDuration.Short
                    )
                )
                return@launch
            }
            try {
                sessionRepository.upsertSession(
                    Session(
                        sessionSubjectId = _state.value.subjectId ?: 0,
                        relatedToSubject = _state.value.relatedToSubject ?: "",
                        date = Instant.now().toEpochMilli(),
                        duration = duration
                    )
                )


            }catch (e: Exception){
                _snackBar.send(
                    ShowSnackBarEvent.ShowSnakeBar(
                        "Couldn't Save Session. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }

}