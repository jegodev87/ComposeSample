package com.sample.test.ui.theme.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val screen: Screens,
    val label: String,
    val icon: ImageVector,
    val route : String
) {
    data object Greetings : BottomNavItem(Screens.Greetings, "Home", Icons.Default.Home, route = Screens.Greetings.route)
    data object Profile : BottomNavItem(Screens.Profile, "Profile", Icons.Default.Person, route = Screens.Profile.route)
    data object Movies : BottomNavItem(Screens.MovieList, "Movies", Icons.Default.DateRange, route = Screens.MovieList.route)
    data object Content : BottomNavItem(Screens.ProfileList, "Content", Icons.Default.AccountBox,route = Screens.ProfileList.route)

    companion object {
        val items = listOf(Greetings, Profile, Movies, Content)
    }
}