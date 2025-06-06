package com.sample.test.ui.theme.machine_test_compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sample.test.ui.theme.mvi_task.TaskEvent
import com.sample.test.ui.theme.mvi_task.TaskState

@Composable
fun CreateTaskPage(state: TaskState, onEvent: (TaskEvent) -> Unit, onSaveOrUpdate: () -> Unit) {


    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (state.editTaskId == null) "Create Task by Entering following Details" else "Please Update or Modify your task status",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.Black,
            textAlign = TextAlign.Center
        )


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.title,
            onValueChange = { onEvent(TaskEvent.TitleChanged(it)) },
            label = {
                Text(text = "Task Title", color = Color.LightGray)
            },
            placeholder = {
                Text(text = "Please enter task title", color = Color.Gray)
            },
            isError = state.titleError != null,
            supportingText = {
                state.titleError?.let { Text(it, color = Color.Red) }
            },

            )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.description,
            onValueChange = { onEvent(TaskEvent.DescriptionChanged(it)) },
            label = {
                Text(text = "Task Description", color = Color.LightGray)
            },
            placeholder = {
                Text(text = "Enter Task Description", color = Color.Gray)
            },
            isError = state.descriptionError != null,
            supportingText = {
                state.descriptionError?.let { Text(it, color = Color.Red) }
            },
        )


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                onEvent(TaskEvent.SaveTask)
                onSaveOrUpdate()
            }) {
            Text(text = if (state.editTaskId == null) "Save Task" else "Update Task")
        }
    }


}