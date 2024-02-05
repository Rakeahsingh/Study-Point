package com.rkcoding.studypoint.sudypoint_features.data.repository

import com.rkcoding.studypoint.sudypoint_features.data.local.dao.SessionDao
import com.rkcoding.studypoint.sudypoint_features.data.local.dao.SubjectDao
import com.rkcoding.studypoint.sudypoint_features.data.local.dao.TaskDao
import com.rkcoding.studypoint.sudypoint_features.data.mapper.toSubject
import com.rkcoding.studypoint.sudypoint_features.data.mapper.toSubjectEntity
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val dao: SubjectDao,
    private val taskDao: TaskDao,
    private val sessionDao: SessionDao
): SubjectRepository {
    override suspend fun upsertSubject(subject: Subject) {
        dao.upsertSubject(subject.toSubjectEntity())
    }

    override suspend fun deleteSubject(subjectId: Int) {
        dao.deleteSubject(subjectId)
        taskDao.deleteTaskBySubjectId(subjectId)
        sessionDao.deleteSessionBySubjectId(subjectId)
    }

    override suspend fun getSubjectById(subjectId: Int): Subject? {
        return dao.getSubjectById(subjectId)?.toSubject()
    }

    override fun getAllSubject(): Flow<List<Subject>> {
        return dao.getAllSubject().map {
            it.map { subject ->
                subject.toSubject()
            }
        }
    }

    override fun getTotalSubjectCount(): Flow<Int> {
        return dao.getTotalSubjectCount()
    }

    override fun getTotalGoalHour(): Flow<Float> {
        return dao.getTotalGoalHour()
    }
}