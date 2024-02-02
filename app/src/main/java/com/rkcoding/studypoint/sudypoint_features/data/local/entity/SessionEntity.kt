package com.rkcoding.studypoint.sudypoint_features.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SessionEntity(
    @PrimaryKey(autoGenerate = true)
    val sessionId: Int? = null,
    val sessionSubjectId: Int,
    val relatedToSubject: String,
    val date: Long,
    val duration: Long,
)
