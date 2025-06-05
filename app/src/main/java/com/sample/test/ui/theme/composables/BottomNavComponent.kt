package com.sample.test.ui.theme.composables

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sample.test.ui.theme.routes.BottomNavItem

@Composable
fun BottomNavComponent(navController: NavController) {

    val currentDestination =
        navController.currentBackStackEntryAsState().value?.destination?.route

    Log.d("TAG", "BottomNavComponent: "+currentDestination)

    BottomNavigation(
        backgroundColor = Color.Transparent, // We already set background on Box
        elevation = 4.dp // Optional: remove default shadow to keep clean look
    ) {

        BottomNavItem.items.forEach { item ->
            println("currentDestination: $currentDestination")
            println("item route: ${item.screen.route}")
            val selectedCorrect = currentDestination == item.screen.route
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
                icon = {  Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = if (currentDestination == item.screen.route) {
                        Color.White
                    } else {
                        Color.Red.copy(alpha = 0.7f)
                    }
                ) },
                label = { Text(
                    text = item.label,
                    color = if (currentDestination == item.screen.route) Color.White else Color.White.copy(alpha = 0.7f)
                ) },
                interactionSource = remember { MutableInteractionSource() }, // Handle interaction source
                selectedContentColor = Color.White, // Use default colors
                unselectedContentColor = Color.Red // Use default colors
            )
        }
    }

}