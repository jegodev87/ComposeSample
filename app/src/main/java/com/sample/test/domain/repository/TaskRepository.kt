package com.sample.test.domain.repository

import com.sample.test.data.local.entity.TaskEntity
import com.sample.test.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskModel>>
    suspend fun insertTask(task: TaskEntity): Long
    suspend fun updateTask(task: TaskEntity) : Int

    fun getTasks(): List<TaskModel>
}