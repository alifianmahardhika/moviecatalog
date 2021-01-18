package com.example.moviecatalog.data

import androidx.lifecycle.LiveData
import com.example.moviecatalog.data.source.local.entity.MovieEntity
import com.example.moviecatalog.data.source.local.entity.TvEntity
import com.example.moviecatalog.vo.Resource

interface MovieDataSource {
    fun getAllMovies(): LiveData<Resource<List<MovieEntity>>>
    fun getDetailMovie(movieId: Int): LiveData<Resource<MovieEntity>>
    fun getAllTv(): LiveData<Resource<List<TvEntity>>>
    fun getDetailTv(tvId: Int): LiveData<Resource<TvEntity>>
    fun getFavoriteMovie(): LiveData<List<MovieEntity>>
    fun getFavoriteTv(): LiveData<List<TvEntity>>
    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)
    fun setFavoriteTv(tv: TvEntity, state: Boolean)
}