package com.rkcoding.studypoint.di

import android.app.Application
import androidx.room.Room
import com.rkcoding.studypoint.sudypoint_features.data.local.StudyDatabase
import com.rkcoding.studypoint.sudypoint_features.data.repository.SessionRepositoryImpl
import com.rkcoding.studypoint.sudypoint_features.data.repository.SubjectRepositoryImpl
import com.rkcoding.studypoint.sudypoint_features.data.repository.TaskRepositoryImpl
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SessionRepository
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SubjectRepository
import com.rkcoding.studypoint.sudypoint_features.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): StudyDatabase{
        return Room.databaseBuilder(
            app,
            StudyDatabase::class.java,
            "study_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSubjectRepository(db: StudyDatabase): SubjectRepository{
        return SubjectRepositoryImpl(db.subjectDao, db.taskDao, db.sessionDao)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: StudyDatabase): TaskRepository{
        return TaskRepositoryImpl( db.taskDao)
    }

    @Provides
    @Singleton
    fun provideSessionRepository(db: StudyDatabase): SessionRepository{
        return SessionRepositoryImpl( db.sessionDao)
    }

}