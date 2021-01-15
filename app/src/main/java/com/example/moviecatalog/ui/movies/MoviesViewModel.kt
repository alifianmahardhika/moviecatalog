package com.example.moviecatalog.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.MovieRepository
import com.example.moviecatalog.data.source.local.entity.MovieEntity

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovies(): LiveData<List<MovieEntity>> = movieRepository.getAllMovies()
    fun getDetailMovie(itemId: Int): LiveData<List<MovieEntity>> =
        movieRepository.getDetailMovie(itemId)
}