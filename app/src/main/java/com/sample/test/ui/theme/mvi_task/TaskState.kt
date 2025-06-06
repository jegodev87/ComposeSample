package com.sample.test.ui.theme.mvi_task

import com.sample.test.domain.model.TaskModel
import kotlinx.serialization.Serializable

@Serializable
data class TaskState(
    val tasks: List<TaskModel> = emptyList(),
    val title: String = "",
    val description: String = "",
    val isEditing: Boolean = false,
    val editTaskId: Int? = null,
    val markAsCompleted : Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val emptyState : Boolean = false,

    // For form validation
    val titleError: String? = null,
    val descriptionError: String? = null
)