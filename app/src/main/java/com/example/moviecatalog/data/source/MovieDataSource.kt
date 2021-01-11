package com.example.moviecatalog.data.source

import androidx.lifecycle.LiveData
import com.example.moviecatalog.data.MovieEntity

interface MovieDataSource {
    fun getAllMovies(): LiveData<List<MovieEntity>>
}