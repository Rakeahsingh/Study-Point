package com.rkcoding.studypoint.sudypoint_features.domain.repository


import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {


    suspend fun upsertSubject(subject: Subject)

    suspend fun deleteSubject(subjectId: Int)

    suspend fun getSubjectById(subjectId: Int): Subject?

    fun getAllSubject(): Flow<List<Subject>>

    fun getTotalSubjectCount(): Flow<Int>

    fun getTotalGoalHour(): Flow<Float?>

}