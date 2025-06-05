package com.sample.test.ui.theme.machine_test_compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sample.test.ui.theme.machine_test_compose.ScreenRoutes
import com.sample.test.ui.theme.machine_test_compose.TaskViewModel

@Composable
fun TaskListScreen(viewModel: TaskViewModel, navController: NavHostController?) {
    val state by viewModel.taskListUiState.collectAsState()
    when (state) {
        is TaskViewModel.TaskListUiState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val taskList = (state as TaskViewModel.TaskListUiState.Success).list
                items(taskList, key = { it.rowID }) { taskDetails ->

                    Card(
                        contentColor = Color.Black, // Content color (e.g., text)
                        backgroundColor = Color.White, // White background for the card
                        shape = RoundedCornerShape(8.dp), // Rounded corners
                        modifier = Modifier
                            .clickable {
                                if (taskDetails.isCompleted) {

                                } else {
                                    viewModel.selectTask(taskDetails)
                                    navController?.navigate(ScreenRoutes.TaskCreate.route)
                                }
                            }
                            .fillMaxWidth()
                            .padding(2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Title : " + taskDetails.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            )
                            Text(
                                text = "Description : " + taskDetails.description,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            )

                            if (taskDetails.isCompleted) {
                                Text(
                                    text = "Task : Completed",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                )
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Checkbox(
                                        checked = taskDetails.isCompleted,
                                        onCheckedChange = { isChecked ->
                                            val updatedTask = taskDetails.copy(isCompleted = isChecked)
                                            viewModel.selectTask(updatedTask)
                                            viewModel.saveOrUpdateTask(updatedTask){

                                            }
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color.Red,
                                            uncheckedColor = Color.Red
                                        ),
                                        modifier = Modifier.padding(0.dp)
                                    )

                                    Text(
                                        text = "Click to complete the task",
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(4.dp)
                                    )

                                }
                            }

                        }


                    }
                }


            }
        }

        is TaskViewModel.TaskListUiState.Empty -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "No Task List Found",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        }

        TaskViewModel.TaskListUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                CircularProgressIndicator(
                    strokeWidth = 1.dp,
                    backgroundColor = Color.Blue
                )
            }
        }
    }
}