package com.rkcoding.studypoint.sudypoint_features.data.repository

import com.rkcoding.studypoint.sudypoint_features.data.local.dao.TaskDao
import com.rkcoding.studypoint.sudypoint_features.data.local.entity.TaskEntity
import com.rkcoding.studypoint.sudypoint_features.data.mapper.toTask
import com.rkcoding.studypoint.sudypoint_features.data.mapper.toTaskEntity
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
        dao.upsertTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(taskId: Int) {
        dao.deleteTask(taskId)
    }


    override suspend fun getTaskById(taskId: Int): Task? {
        return dao.getTaskById(taskId)?.toTask()
    }

    override fun getUpcomingTaskForSubjectId(subjectId: Int): Flow<List<Task>> {
        return dao.getTaskForSubjectId(subjectId).map {
            it.map { task ->
                task.toTask()
            }
        }.map {
            it.filter { tasks -> tasks.isCompleted.not() }
        }.map { task ->  sortedTask(task) }
    }

    override fun getCompletedTaskForSubjectId(subjectId: Int): Flow<List<Task>> {
        return dao.getTaskForSubjectId(subjectId).map {
            it.map { task ->
                task.toTask()
            }
        }.map {
            it.filter { tasks -> tasks.isCompleted }
        }.map { task ->  sortedTask(task) }
    }


    override fun getAllUpcomingTask(): Flow<List<Task>> {
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