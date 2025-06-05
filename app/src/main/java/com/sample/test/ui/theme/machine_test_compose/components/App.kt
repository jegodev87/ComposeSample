package com.sample.test.ui.theme.machine_test_compose.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.test.ui.theme.machine_test_compose.ScreenRoutes
import com.sample.test.ui.theme.machine_test_compose.TaskViewModel

@Composable
 fun MachineTestApp(paddingValues: PaddingValues, navController: NavHostController) {

    val viewModel: TaskViewModel = hiltViewModel()
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        navController = navController,
        startDestination = ScreenRoutes.TaskList.route
    ) {
        composable(ScreenRoutes.TaskList.route) {
            TaskListScreen(viewModel = viewModel, navController = navController)
        }

        composable(ScreenRoutes.TaskCreate.route) {
            CreateTaskPage(viewModel, navController)
        }
    }
}