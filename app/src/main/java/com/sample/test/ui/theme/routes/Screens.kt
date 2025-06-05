package com.sample.test.ui.theme.routes

import com.sample.test.models.Movie
import com.sample.test.models.Screen
import kotlinx.serialization.Serializable


@Serializable
sealed class Screens(open val route: String) {

    @Serializable
    data object Greetings : Screens("greetings")

    @Serializable
    data object Profile : Screens("profile")

    @Serializable
    data object MovieList : Screens("movies")

    @Serializable
    data object ProfileList : Screens("profile")

    @Serializable
    data class MovieDetails(val movie: Movie) : Screens("movie_details/${movie.name}")
}
