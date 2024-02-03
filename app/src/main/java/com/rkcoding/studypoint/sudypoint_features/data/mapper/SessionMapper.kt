package com.rkcoding.studypoint.sudypoint_features.data.mapper

import com.rkcoding.studypoint.sudypoint_features.data.local.entity.SessionEntity
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session

fun SessionEntity.toSession(): Session{
    return Session(
        sessionId = sessionId,
        sessionSubjectId = sessionSubjectId,
        relatedToSubject = relatedToSubject,
        date = date,
        duration = duration
    )
}

fun Session.toSessionEntity(): SessionEntity{
    return SessionEntity(
        sessionId = sessionId,
        sessionSubjectId = sessionSubjectId,
        relatedToSubject = relatedToSubject,
        date = date,
        duration = duration
    )
}