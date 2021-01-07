package com.example.moviecatalog.ui.movies

import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.MovieEntity
import com.example.moviecatalog.utils.Data

class MoviesViewModel : ViewModel() {

    fun getMovies(): List<MovieEntity> = Data.generateMoviesData()
}