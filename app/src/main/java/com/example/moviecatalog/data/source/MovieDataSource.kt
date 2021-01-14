package com.example.moviecatalog.data.source

import androidx.lifecycle.LiveData
import com.example.moviecatalog.data.MovieEntity
import com.example.moviecatalog.data.TvEntity

interface MovieDataSource {
    fun getAllMovies(): LiveData<List<MovieEntity>>
    fun getDetailMovie(movieId: Int): LiveData<List<MovieEntity>>
    fun getAllTv(): LiveData<List<TvEntity>>
    fun getDetailTv(tvId: Int): LiveData<List<TvEntity>>
}