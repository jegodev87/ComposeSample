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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sample.test.ui.theme.machine_test_compose.ScreenRoutes
import com.sample.test.ui.theme.machine_test_compose.TaskViewModel

@Composable
fun CreateTaskPage(viewModel: TaskViewModel, navController: NavHostController) {

    val selectedTask by viewModel.selectedTask.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (selectedTask == null) "Create Task by Entering following Details" else "Please Update or Modify your task status",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.Black,
            textAlign = TextAlign.Center
        )


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.mTaskTitleEntered,
            onValueChange = viewModel::onTitleChange,
            label = {
                Text(text = "Task Title", color = Color.LightGray)
            },
            placeholder = {
                Text(text = "Please enter task title", color = Color.Gray)
            },
            isError = viewModel.titleErrorMessage != null,
            supportingText = {
                viewModel.titleErrorMessage?.let { Text(it, color = Color.Red) }
            }

            )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.mTaskDescriptionEntered,
            onValueChange = viewModel::onDescriptionChange,
            label = {
                Text(text = "Task Description", color = Color.LightGray)
            },
            placeholder = {
                Text(text = "Enter Task Description", color = Color.Gray)
            },
            isError = viewModel.descriptionErrorMessage != null,
            supportingText = {
                viewModel.descriptionErrorMessage?.let { Text(it, color = Color.Red) }
            }
        )


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                viewModel.saveOrUpdateTask(selectedTask,{
                    navController.navigate(ScreenRoutes.TaskList.route){
                        this.launchSingleTop = true
                        popUpTo(ScreenRoutes.TaskCreate.route){
                            inclusive = true
                        }
                    }
                })
            }) {
            Text(text = if (selectedTask == null) "Save Task" else "Update Task")
        }
    }


}