package com.rkcoding.studypoint.sudypoint_features.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.rkcoding.studypoint.sudypoint_features.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Query("delete from taskentity where taskId= :taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("delete from taskentity where taskSubjectId= :subjectId")
    suspend fun deleteTaskBySubjectId(subjectId: Int)

    @Query("select * from taskentity where taskId= :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity?

    @Query("select * from taskentity where taskSubjectId= :subjectId")
    fun getTaskForSubjectId(subjectId: Int): Flow<List<TaskEntity>>

    @Query("select * from taskentity")
    fun getAllTask(): Flow<List<TaskEntity>>

}