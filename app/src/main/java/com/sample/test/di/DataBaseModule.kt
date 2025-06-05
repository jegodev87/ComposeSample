package com.sample.test.di

import android.content.Context
import androidx.room.Room
import com.sample.test.data.local.dao.TaskDao
import com.sample.test.data.local.db.TaskDataBase
import com.sample.test.data.local.TaskRepositoryImpl
import com.sample.test.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TaskDataBase {
        return Room.databaseBuilder(
            context,
            TaskDataBase::class.java,
            "task_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTaskDao(db: TaskDataBase): TaskDao = db.taskDao()

    @Singleton
    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository = TaskRepositoryImpl(taskDao)
}
