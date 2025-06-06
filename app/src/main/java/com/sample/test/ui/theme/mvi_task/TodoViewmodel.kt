package com.sample.test.ui.theme.mvi_task

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.test.domain.mapper.toEntity
import com.sample.test.domain.model.TaskModel
import com.sample.test.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewmodel @Inject constructor(private val repository: TaskRepository) : ViewModel() {


    var state by mutableStateOf(TaskState())
        private set

    init {
        loadTasks(showLoading = true)
    }

    private fun loadTasks(showLoading : Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoading = showLoading, error = null)
            try {
                delay(500)
                val tasks = repository.getTasks()
                Log.d("TAG", "loadTasks: +"+tasks)
                state = state.copy(tasks = tasks, isLoading = false, emptyState = tasks.isEmpty())
            } catch (e: Exception) {
                state = state.copy(error = "Failed to load tasks", isLoading = false)
            }
        }
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.TitleChanged -> state = state.copy(title = event.title,  titleError = null)
            is TaskEvent.DescriptionChanged -> state = state.copy(description = event.description,  descriptionError = null)

            is TaskEvent.SaveTask -> {
                val titleValid = state.title.isNotBlank()
                val descValid = state.description.isNotBlank()

                if (!titleValid || !descValid) {
                    state = state.copy(
                        titleError = if (!titleValid) "Title field required" else null,
                        descriptionError = if (!descValid) "Description field required" else null
                    )
                    return
                }

                viewModelScope.launch {
                  val isSuccess =  if (state.isEditing && state.editTaskId != null) {
                        val rowsUpdated =  repository.updateTask(
                            TaskModel(
                                state.editTaskId ?: 0,
                                state.title,
                                state.description,
                                state.markAsCompleted
                            ).toEntity()
                        )

                      rowsUpdated > 0
                    } else {
                        val insertedId =  repository.insertTask(
                            TaskModel(
                                title = state.title,
                                description = state.description,
                                rowID = 0,
                                isCompleted = false
                            ).toEntity()
                        )
                        insertedId > 0L
                    }

                    if (isSuccess){
                        clearForm()
                        loadTasks()
                    }

                }
            }

            is TaskEvent.ToggleComplete -> {
                viewModelScope.launch {
                    val task = state.tasks.find { it.rowID == event.taskId } ?: return@launch
                    repository.updateTask(task.copy(isCompleted = true).toEntity())
                    loadTasks()
                }
            }

            is TaskEvent.EditTask -> {
                state = state.copy(
                    title = event.task.title,
                    description = event.task.description,
                    isEditing = true,
                    editTaskId = event.task.rowID
                )
            }

            TaskEvent.ClearForm -> clearForm()
            is TaskEvent.EditSelection -> {
                state = state.copy(
                    title = event.task.title,
                    description = event.task.description,
                    isEditing = true,
                    editTaskId = event.task.rowID
                )
            }
        }
    }

    private fun clearForm() {
        state = state.copy(
            title = "",
            description = "",
            isEditing = false,
            editTaskId = null
        )
    }




}