package com.rkcoding.studypoint.sudypoint_features.data.repository

import com.rkcoding.studypoint.sudypoint_features.data.local.dao.TaskDao
import com.rkcoding.studypoint.sudypoint_features.data.local.entity.TaskEntity
import com.rkcoding.studypoint.sudypoint_features.data.mapper.toTask
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task
import com.rkcoding.studypoint.sudypoint_features.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao
): TaskRepository {
    override suspend fun upsertTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTaskBySubjectId(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        TODO("Not yet implemented")
    }

    override fun getTaskForSubjectId(subjectId: Int): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getAllTask(): Flow<List<Task>> {
        return dao.getAllTask().map {
            it.map { task ->
                task.toTask()
            }
        }.map {
            it.filter { tasks -> tasks.isCompleted.not() }
        }.map { task ->  sortedTask(task) }
    }

    private fun sortedTask(task: List<Task>): List<Task>{
        return task.sortedWith(compareBy<Task>{ it.dueDate }.thenByDescending { it.priority })
    }
}