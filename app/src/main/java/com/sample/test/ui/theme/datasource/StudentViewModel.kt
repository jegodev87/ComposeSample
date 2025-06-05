package com.sample.test.ui.theme.datasource

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.test.models.MovieResponseItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {

    var text by mutableStateOf("The Name of the Student by default is Jeevan")

    private val mMovieListStateFlow = MutableStateFlow<List<MovieResponseItem>>(emptyList())
    val movieList get() = mMovieListStateFlow.asStateFlow()


    fun getMovieList() = viewModelScope.launch {
        delay(3000L)
        val movieCategories = FakeMovieRepo.parseMovieJson(FakeMovieRepo.mockMovieResponse)
        mMovieListStateFlow.tryEmit(movieCategories)
    }
}