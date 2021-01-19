package com.example.moviecatalog.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviecatalog.data.MovieRepository
import com.example.moviecatalog.data.source.local.entity.MovieEntity
import com.example.moviecatalog.vo.Resource

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movieRepository.getAllMovies()

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> = movieRepository.getFavoriteMovie()
    val movieId = MutableLiveData<Int>()

    fun setMovieId(Id: Int) {
        this.movieId.value = Id
    }

    var selectedMovie: LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(movieId) { mMovieId ->
            movieRepository.getDetailMovie(mMovieId)
        }

    fun setFavoriteMovie() {
        val movieResource = selectedMovie.value
        if (movieResource != null) {
            val movieEntity = movieResource.data
            if (movieEntity != null) {
                val newState = !movieEntity.isFavorite
                movieRepository.setFavoriteMovie(movieEntity, newState)
            }
        }
    }
}