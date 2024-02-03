package com.rkcoding.studypoint.sudypoint_features.domain.repository


import com.rkcoding.studypoint.sudypoint_features.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun upsertTask(task: Task)

    suspend fun deleteTask(taskId: Int)

    suspend fun deleteTaskBySubjectId(subjectId: Int)

    suspend fun getTaskById(taskId: Int): Task?

    fun getTaskForSubjectId(subjectId: Int): Flow<List<Task>>

    fun getAllTask(): Flow<List<Task>>

}