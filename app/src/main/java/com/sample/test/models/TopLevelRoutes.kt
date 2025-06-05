package com.sample.test.models

import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

sealed class Screen(val route: String) {
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
}