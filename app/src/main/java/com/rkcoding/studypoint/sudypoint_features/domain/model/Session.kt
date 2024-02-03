package com.rkcoding.studypoint.sudypoint_features.domain.model

data class Session(
    val sessionId: Int? = null,
    val sessionSubjectId: Int,
    val relatedToSubject: String,
    val date: Long,
    val duration: Long,
)
