package com.rkcoding.studypoint.sudypoint_features.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.rkcoding.studypoint.sudypoint_features.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Upsert
    suspend fun upsertSubject(subject: SubjectEntity)

    @Query("delete from subjectentity where subjectId = :subjectId")
    suspend fun deleteSubject(subjectId: Int)

    @Query("select * from subjectentity where subjectId= :subjectId")
    suspend fun getSubjectById(subjectId: Int): SubjectEntity?

    @Query("select * from subjectentity")
    fun getAllSubject(): Flow<List<SubjectEntity>>

    @Query("select count(*) from subjectentity")
    fun getTotalSubjectCount(): Flow<Int>

    @Query("select sum(goalHours) from subjectentity")
    fun getTotalGoalHour(): Flow<Float>

}