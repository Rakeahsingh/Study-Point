package com.rkcoding.studypoint.sudypoint_features.data.repository

import com.rkcoding.studypoint.sudypoint_features.data.local.dao.SessionDao
import com.rkcoding.studypoint.sudypoint_features.data.local.entity.SessionEntity
import com.rkcoding.studypoint.sudypoint_features.data.mapper.toSession
import com.rkcoding.studypoint.sudypoint_features.data.mapper.toSessionEntity
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val dao: SessionDao
): SessionRepository {

    override suspend fun upsertSession(session: Session) {
        dao.upsertSession(session.toSessionEntity())
    }

    override suspend fun deleteSession(session: Session) {
        TODO("Not yet implemented")
    }

    override fun getAllSession(): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getRecentFiveSessions(): Flow<List<Session>> {
        return dao.getAllSession().map {
            it.map { session ->
                session.toSession()
            }
        }.take(count = 5)
    }

    override fun getRecentTenSessionForSubject(subjectId: Int): Flow<List<Session>> {
        return dao.getRecentSessionForSubject(subjectId).map {
            it.map { session ->
                session.toSession()
            }
        }.take(count = 10)
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return dao.getTotalSessionDuration()
    }

    override fun getTotalSessionDurationBySubjectId(subjectId: Int): Flow<Long> {
        return dao.getTotalSessionDurationById(subjectId)
    }
}