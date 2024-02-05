package com.rkcoding.studypoint.sudypoint_features.domain.repository

import androidx.room.Delete
import androidx.room.Query
import com.rkcoding.studypoint.sudypoint_features.data.local.entity.SessionEntity
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun upsertSession(session: Session)

    suspend fun deleteSession(session: Session)

    fun getAllSession(): Flow<List<Session>>

    fun getRecentFiveSessions(): Flow<List<Session>>

    fun getRecentTenSessionForSubject(subjectId: Int): Flow<List<Session>>

    fun getTotalSessionDuration(): Flow<Long?>

    fun getTotalSessionDurationBySubjectId(subjectId: Int): Flow<Long?>

}