package com.example.moviecatalog.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.moviecatalog.data.source.local.entity.MovieEntity
import com.example.moviecatalog.data.source.local.entity.TvEntity
import com.example.moviecatalog.vo.Resource

interface MovieDataSource {
    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getDetailMovie(movieId: Int): LiveData<Resource<MovieEntity>>
    fun getAllTv(): LiveData<Resource<PagedList<TvEntity>>>
    fun getDetailTv(tvId: Int): LiveData<Resource<TvEntity>>
    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>>
    fun getFavoriteTv(): LiveData<PagedList<TvEntity>>
    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)
    fun setFavoriteTv(tv: TvEntity, state: Boolean)
}