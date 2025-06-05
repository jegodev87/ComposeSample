package com.sample.test.domain.mapper

import com.sample.test.data.local.entity.TaskEntity
import com.sample.test.domain.model.TaskModel

fun TaskModel.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.rowID,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted
    )
}

fun TaskEntity.toModel(): TaskModel {
    return TaskModel(
        rowID = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted
    )
}

fun List<TaskEntity>.toModelList(): List<TaskModel> = map { it.toModel() }

fun List<TaskModel>.toEntityList(): List<TaskEntity> = map { it.toEntity() }
