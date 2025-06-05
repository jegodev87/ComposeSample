package com.sample.test.ui.theme.machine_test_compose

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.test.domain.mapper.toEntity
import com.sample.test.domain.repository.TaskRepository
import com.sample.test.domain.model.TaskModel
import com.sample.test.ui.theme.viewmodels.UserViewModel.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    private val _savedTaskList = MutableStateFlow<TaskListUiState>(TaskListUiState.Loading)

    val savedTaskList get() = _savedTaskList

    var mTaskTitleEntered by mutableStateOf("")
        private set

    var mTaskDescriptionEntered by mutableStateOf("")
        private set


    var mTaskCompletionStatus by mutableStateOf(false)
        private set

    var titleErrorMessage by mutableStateOf<String?>(null)
    private set

    var descriptionErrorMessage by mutableStateOf<String?>(null)
        private set

    fun onTitleChange(title : String) {
        mTaskTitleEntered = title
        if (title.isNotBlank()) {
            titleErrorMessage = null
        }
    }

    fun onDescriptionChange(description : String) {
        mTaskDescriptionEntered = description
        if (description.isNotBlank()) {
            descriptionErrorMessage = null
        }
    }







    fun saveOrUpdateTask(currentTask: TaskModel?, onComplete: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {



        val task = TaskModel(
            rowID = currentTask?.rowID ?: 0,
            title = mTaskTitleEntered,
            description = mTaskDescriptionEntered,
            isCompleted = mTaskCompletionStatus
        )

        if (!validateInputs()) {
            val message = titleErrorMessage ?: descriptionErrorMessage ?: "Invalid input"
            viewModelScope.launch {
                taskSaveUpdateUiStatus.emit(TaskFormUiState.ValidationFailed(message))
            }
        }else{
            val isSuccess = if (task.rowID == 0) {
                val insertedId = repository.insertTask(task.toEntity())
                insertedId > 0L
            } else {
                val rowsUpdated = repository.updateTask(task.toEntity())
                rowsUpdated > 0
            }

            Log.d("TAG", "saveOrUpdateTask: $task")

            if (isSuccess) {
                withContext(Dispatchers.Main) {
                    onComplete()
                }
            }
        }


    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (mTaskTitleEntered.isBlank()) {
            titleErrorMessage = "Title cannot be empty"
            isValid = false
        } else {
            titleErrorMessage = null
        }

        if (mTaskDescriptionEntered.isBlank()) {
            descriptionErrorMessage = "Description cannot be empty"
            isValid = false
        } else {
            descriptionErrorMessage = null
        }

        return isValid
    }

    /**
     * Fetching data from db
     *
     */
    val taskListUiState: StateFlow<TaskListUiState> = repository.getAllTasks()
        .map { list ->
            when {
                list.isEmpty() -> TaskListUiState.Empty(true)
                else -> TaskListUiState.Success(list)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TaskListUiState.Loading
        )

    private val _selectedTask = MutableStateFlow<TaskModel?>(null)
    val selectedTask: StateFlow<TaskModel?> = _selectedTask.asStateFlow()

    fun selectTask(task: TaskModel) {
        _selectedTask.value = task
        mTaskTitleEntered = task.title
        mTaskDescriptionEntered = task.description
        mTaskCompletionStatus = task.isCompleted
    }



    val taskSaveUpdateUiStatus = MutableStateFlow<TaskFormUiState>(TaskFormUiState.Idle)


    sealed class TaskListUiState {
        data class Success(val list: List<TaskModel>) : TaskListUiState()
        data object Loading : TaskListUiState()
        data class Empty(val isEmpty: Boolean) : TaskListUiState()
    }

    sealed class TaskFormUiState {
        data object Idle : TaskFormUiState()
        data class SaveTaskSuccess(val taskId: Long) : TaskFormUiState()
        data class ValidationFailed(val message: String) : TaskFormUiState()
    }


}