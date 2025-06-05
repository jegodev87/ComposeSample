package com.sample.test.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.test.data.local.dao.TaskDao
import com.sample.test.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
