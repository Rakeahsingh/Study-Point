package com.rkcoding.studypoint.sudypoint_features.presentation.session_screen

import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject

sealed class SessionEvent {

    data class OnRelatedSubjectChange(val subject: Subject): SessionEvent()

    data class SaveSession(val duration: Long): SessionEvent()

    data class OnDeleteSessionButtonClick(val session: Session): SessionEvent()

    data class UpdateSubjectIdAndRelatedSubject(
        val subjectId: Int?,
        val relatedToSubject: String?
    ): SessionEvent()

    data object DeleteSession: SessionEvent()

    data object NotifyToUpdateSubject: SessionEvent()

}