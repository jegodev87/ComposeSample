package com.sample.test.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TaskModel(
    val rowID: Int = 0,
    val title: String, val description: String, var isCompleted: Boolean
)
