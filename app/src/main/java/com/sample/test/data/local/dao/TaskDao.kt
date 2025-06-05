package com.sample.test.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sample.test.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    /*@Query("SELECT * FROM TaskEntity")
    suspend fun getAll(): List<TaskEntity>*/

    @Query("SELECT * FROM TaskEntity ORDER BY id DESC")
    fun getAll(): List<TaskEntity> // Changed to Flow

    @Query("SELECT * FROM TaskEntity")
    fun getAllTasks(): Flow<List<TaskEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity): Long

    @Update
    suspend fun update(task: TaskEntity) : Int
}