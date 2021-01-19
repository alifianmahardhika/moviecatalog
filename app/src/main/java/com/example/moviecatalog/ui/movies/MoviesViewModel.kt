package com.example.moviecatalog.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.MovieRepository
import com.example.moviecatalog.data.source.local.entity.MovieEntity
import com.example.moviecatalog.vo.Resource

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getMovies(): LiveData<Resource<List<MovieEntity>>> = movieRepository.getAllMovies()
    fun getDetailMovie(itemId: Int): LiveData<Resource<MovieEntity>> =
        movieRepository.getDetailMovie(itemId)

    fun getFavoriteMovies(): LiveData<List<MovieEntity>> = movieRepository.getFavoriteMovie()
}