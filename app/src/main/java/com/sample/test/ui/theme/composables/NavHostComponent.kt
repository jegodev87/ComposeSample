package com.sample.test.ui.theme.composables

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.test.ui.theme.activities.Greeting
import com.sample.test.ui.theme.activities.ProfileScreen
import com.sample.test.ui.theme.routes.Screens

@Composable
fun NavHostComponent(navController: NavHostController, innerPadding: PaddingValues) {

    NavHost(
        navController = navController,
        startDestination = Screens.Greetings,
        modifier = Modifier.padding(0.dp),
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(100)) + fadeIn(
                animationSpec = tween(100)
            )
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(100)) + fadeOut(
                animationSpec = tween(100)
            )
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(100)) + fadeIn(
                animationSpec = tween(100)
            )
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(100)) + fadeOut(
                animationSpec = tween(100)
            )
        }
    )
     {


        composable<Screens.Greetings>{
            Greeting("Jeev", onTextChange = "Something went wrong")
        }

        composable<Screens.Profile> {
            ProfileScreen()
        }

        composable<Screens.MovieList> {
            MovieList()
        }

         composable<Screens.ProfileList>{
             LazyColumn(
                 contentPadding = PaddingValues(bottom = 80.dp),
                 modifier = Modifier
                     .fillMaxSize()
                     .systemBarsPadding() // adds padding for status bar and nav bar
             ) {
                 items(101) {
                     Text("Item $it")
                 }
             }
         }
        /* composable(BottomNavItem.Home.route) {
           Greeting("Jeev", onTextChange = "Something went wrong")
        }

        composable(BottomNavItem.Profile.route) {
           ProfileScreen()
        }*/
    }


}