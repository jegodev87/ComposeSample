package com.sample.test.ui.theme.machine_test_compose




sealed class ScreenRoutes(val route: String) {
    data object TaskList : ScreenRoutes("task-list")
    data object TaskCreate : ScreenRoutes("task-create")
}