package com.sample.test.ui.theme.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sample.test.ui.theme.machine_test_compose.ScreenRoutes
import com.sample.test.ui.theme.machine_test_compose.components.MachineTestApp
import com.sample.test.ui.theme.machine_test_compose.components.TopBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SampleTaskActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            Scaffold(
                topBar = {
                    TopBar()
                },
                floatingActionButton = {
                    if (currentRoute == ScreenRoutes.TaskList.route) {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate(ScreenRoutes.TaskCreate.route) {
                                    launchSingleTop = true
                                }
                            },
                            containerColor = Color.Blue,
                            contentColor = Color.White
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Show Task List"
                            )

                        }
                    }

                }
            ) {
                MachineTestApp(it, navController)
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    private fun LandingPage() {
        MachineTestApp(
            PaddingValues(16.dp),
            navController = rememberNavController()
        )
    }
}