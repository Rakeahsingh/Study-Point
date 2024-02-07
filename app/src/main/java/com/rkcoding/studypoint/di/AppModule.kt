package com.rkcoding.studypoint.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.room.Room
import com.rkcoding.studypoint.R
import com.rkcoding.studypoint.core.utils.Constants
import com.rkcoding.studypoint.core.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.rkcoding.studypoint.sudypoint_features.data.local.StudyDatabase
import com.rkcoding.studypoint.sudypoint_features.data.repository.SessionRepositoryImpl
import com.rkcoding.studypoint.sudypoint_features.data.repository.SubjectRepositoryImpl
import com.rkcoding.studypoint.sudypoint_features.data.repository.TaskRepositoryImpl
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SessionRepository
import com.rkcoding.studypoint.sudypoint_features.domain.repository.SubjectRepository
import com.rkcoding.studypoint.sudypoint_features.domain.repository.TaskRepository
import com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component.ServiceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
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

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager{
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideNotificationBuilder(@ApplicationContext context: Context): NotificationCompat.Builder{
        return NotificationCompat
            .Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Study Session")
            .setContentText("00:00:00")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setContentIntent(ServiceHelper.clickPendingIntent(context))
    }

}