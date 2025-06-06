package com.sample.test.ui.theme.mvi_task

import com.sample.test.domain.model.TaskModel

sealed class TaskEvent {
    data class TitleChanged(val title: String) : TaskEvent()
    data class DescriptionChanged(val description: String) : TaskEvent()
    data object SaveTask : TaskEvent()
    data class ToggleComplete(val taskId: Int) : TaskEvent()
    data class EditTask(val task: TaskModel) : TaskEvent()
    data object ClearForm : TaskEvent()
    data class EditSelection(val task: TaskModel) : TaskEvent()
}