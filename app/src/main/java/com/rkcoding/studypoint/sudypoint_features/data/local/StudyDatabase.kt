package com.rkcoding.studypoint.sudypoint_features.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rkcoding.studypoint.sudypoint_features.data.local.dao.SessionDao
import com.rkcoding.studypoint.sudypoint_features.data.local.dao.SubjectDao
import com.rkcoding.studypoint.sudypoint_features.data.local.dao.TaskDao
import com.rkcoding.studypoint.sudypoint_features.data.local.entity.SessionEntity
import com.rkcoding.studypoint.sudypoint_features.data.local.entity.SubjectEntity
import com.rkcoding.studypoint.sudypoint_features.data.local.entity.TaskEntity

@Database(
    entities = [SubjectEntity::class, TaskEntity::class, SessionEntity::class],
    version = 1
)
@TypeConverters(ColorListConverter::class)
abstract class StudyDatabase: RoomDatabase() {

    abstract val subjectDao: SubjectDao
    abstract val taskDao: TaskDao
    abstract val sessionDao: SessionDao

}