package com.rkcoding.studypoint.sudypoint_features.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val relatedToSubject: String,
    val isCompleted: Boolean,
    val taskSubjectId: Int,
    @PrimaryKey(autoGenerate = true)
    val taskId: Int? = null
)
