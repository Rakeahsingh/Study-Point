package com.rkcoding.studypoint.sudypoint_features.data.mapper

import com.rkcoding.studypoint.sudypoint_features.data.local.entity.TaskEntity
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task

fun TaskEntity.toTask(): Task{
    return Task(
        title = title,
        description = description,
        dueDate = dueDate,
        priority = priority,
        relatedToSubject = relatedToSubject,
        isCompleted = isCompleted,
        taskSubjectId = taskSubjectId,
        taskId = taskId
    )
}

fun Task.toTaskEntity(): TaskEntity{
    return TaskEntity(
        title = title,
        description = description,
        dueDate = dueDate,
        priority = priority,
        relatedToSubject = relatedToSubject,
        isCompleted = isCompleted,
        taskSubjectId = taskSubjectId,
        taskId = taskId
    )
}