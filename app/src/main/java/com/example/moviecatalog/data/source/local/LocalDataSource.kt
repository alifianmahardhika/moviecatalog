package com.example.moviecatalog.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.moviecatalog.data.source.local.entity.MovieEntity
import com.example.moviecatalog.data.source.local.entity.TvEntity
import com.example.moviecatalog.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val mMovieDao: MovieDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getMovies()
    fun getAllTvs(): DataSource.Factory<Int, TvEntity> = mMovieDao.getTvs()
    fun getDetailMovie(movieId: Int): LiveData<MovieEntity> = mMovieDao.getMovieById(movieId)
    fun getDetailTv(tvId: Int): LiveData<TvEntity> = mMovieDao.getTvById(tvId)
    fun insertMovies(movies: List<MovieEntity>) = mMovieDao.insertMovies(movies)
    fun insertTvs(tvs: List<TvEntity>) = mMovieDao.insertTvs(tvs)
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getFavoriteMovies()
    fun getFavoriteTvs(): DataSource.Factory<Int, TvEntity> = mMovieDao.getFavoriteTvs()
    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        mMovieDao.updateMovie(movie)
    }

    fun setFavoriteTv(tv: TvEntity, newState: Boolean) {
        tv.isFavorite = newState
        mMovieDao.updateTv(tv)
    }
}