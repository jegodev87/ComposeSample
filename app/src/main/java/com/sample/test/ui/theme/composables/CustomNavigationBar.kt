package com.sample.test.ui.theme.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sample.test.ui.theme.routes.BottomNavItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomNavigationBar(navController : NavController) {

    val bottomNavController = rememberNavController()
    val bottomNavBarBgColor = Color.Black

    Box(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        val currentDestination =
            navController.currentBackStackEntryAsState().value?.destination?.route

        BottomNavigation {

            BottomNavItem.items.forEach { item ->
                BottomNavigationItem(
                    selected = currentDestination == item.screen.route,
                    onClick = {
                        if (currentDestination != item.screen.route) {
                            navController.navigate(item.screen) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) },
                    interactionSource = remember { MutableInteractionSource() }, // Handle interaction source
                    selectedContentColor = Color.Unspecified, // Use default colors
                    unselectedContentColor = Color.Unspecified // Use default colors
                )
            }
        }
    }
}