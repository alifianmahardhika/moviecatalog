package com.example.moviecatalog.data

import androidx.lifecycle.LiveData
import com.example.moviecatalog.data.source.local.entity.MovieEntity
import com.example.moviecatalog.data.source.local.entity.TvEntity

interface MovieDataSource {
    fun getAllMovies(): LiveData<List<MovieEntity>>
    fun getDetailMovie(movieId: Int): LiveData<List<MovieEntity>>
    fun getAllTv(): LiveData<List<TvEntity>>
    fun getDetailTv(tvId: Int): LiveData<List<TvEntity>>
}