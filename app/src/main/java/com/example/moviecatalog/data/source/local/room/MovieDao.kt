package com.example.moviecatalog.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.moviecatalog.data.source.local.entity.MovieEntity
import com.example.moviecatalog.data.source.local.entity.TvEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movietable")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvtable")
    fun getTvs(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM movietable WHERE isFavorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvtable WHERE isFavorite = 1")
    fun getFavoriteTvs(): DataSource.Factory<Int, TvEntity>

    @Transaction
    @Query("SELECT * FROM movietable WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM tvtable WHERE tvId = :tvId")
    fun getTvById(tvId: Int): LiveData<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvs(tvs: List<TvEntity>)

    @Update
    fun updateTv(tv: TvEntity)
}