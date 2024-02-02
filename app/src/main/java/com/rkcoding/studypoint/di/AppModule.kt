package com.rkcoding.studypoint.di

import android.app.Application
import androidx.room.Room
import com.rkcoding.studypoint.sudypoint_features.data.local.StudyDatabase
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

}