package com.sample.test.data.local

import com.sample.test.data.local.dao.TaskDao
import com.sample.test.data.local.entity.TaskEntity
import com.sample.test.domain.mapper.toModel
import com.sample.test.domain.mapper.toModelList
import com.sample.test.domain.model.TaskModel
import com.sample.test.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TaskRepository {

    override fun getAllTasks(): Flow<List<TaskModel>> {
        return taskDao.getAllTasks().map { entityList ->
            entityList.map { it.toModel() }
        }
    }
    override suspend fun insertTask(task: TaskEntity) = taskDao.insert(task)

    override suspend fun updateTask(task: TaskEntity) = taskDao.update(task)
    override fun getTasks(): List<TaskModel>  = taskDao.getTasks().toModelList()
}
