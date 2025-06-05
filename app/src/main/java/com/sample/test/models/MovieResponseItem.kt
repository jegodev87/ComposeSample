package com.sample.test.models

import kotlinx.serialization.Serializable


data class MovieResponseItem(
    val key: String,
    val movies: List<Movie>
)

@Serializable
data class Movie(
    val description: String,
    val image_url: String,
    val name: String,
    val release_year: Int
)
