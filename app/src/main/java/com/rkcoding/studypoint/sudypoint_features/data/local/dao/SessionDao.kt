package com.rkcoding.studypoint.sudypoint_features.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.rkcoding.studypoint.sudypoint_features.data.local.entity.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Upsert
    suspend fun upsertSession(session: SessionEntity)

    @Delete
    suspend fun deleteSession(session: SessionEntity)

    @Query("delete from sessionentity where sessionSubjectId= :subjectId")
    suspend fun deleteSessionBySubjectId(subjectId: Int)

    @Query("select * from sessionentity")
    fun getAllSession(): Flow<List<SessionEntity>>

    @Query("select * from sessionentity where sessionSubjectId= :subjectId")
    fun getRecentSessionForSubject(subjectId: Int): Flow<List<SessionEntity>>

    @Query("select sum(duration) from sessionentity")
    fun getTotalSessionDuration(): Flow<Long>

    @Query("select sum(duration) from sessionentity where sessionSubjectId= :subjectId")
    fun getTotalSessionDurationById(subjectId: Int): Flow<Long>

}