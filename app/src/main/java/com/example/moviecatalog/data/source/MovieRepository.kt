package com.example.moviecatalog.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviecatalog.data.MovieEntity
import com.example.moviecatalog.data.TvEntity
import com.example.moviecatalog.data.source.remote.RemoteDataSource
import com.example.moviecatalog.data.source.remote.response.ItemsItem

class MovieRepository private constructor(private val remoteDataSource: RemoteDataSource) :
    MovieDataSource {
    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(remoteData: RemoteDataSource): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData)
            }
    }

    override fun getAllMovies(): LiveData<List<MovieEntity>> {
        val movieResponseResult = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getRemoteMovies(object : RemoteDataSource.LoadMoviesCallback {
            override fun onAllMoviesReceived(responseMovies: List<ItemsItem>) {
                val listMovie = ArrayList<MovieEntity>()
                for (response in responseMovies) {
                    val movie = MovieEntity(
                        response.id,
                        response.title,
                        response.overview,
                        response.posterPath
                    )
                    listMovie.add(movie)
                }
                movieResponseResult.postValue(listMovie)
            }
        })
        return movieResponseResult
    }

    override fun getDetailMovie(movieId: Int): LiveData<List<MovieEntity>> {
        val responseResult = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getRemoteMovies(object : RemoteDataSource.LoadMoviesCallback {
            override fun onAllMoviesReceived(responseMovies: List<ItemsItem>) {
                val listDMovie = ArrayList<MovieEntity>()
                for (response in responseMovies) {
                    if (response.id == movieId) {
                        val movie = MovieEntity(
                            response.id,
                            response.title,
                            response.overview,
                            response.posterPath
                        )
                        listDMovie.add(movie)
                    }
                    responseResult.postValue(listDMovie)
                }
            }

        })
        return responseResult
    }

    override fun getAllTv(): LiveData<List<TvEntity>> {
        val responseResult = MutableLiveData<List<TvEntity>>()
        remoteDataSource.getRemoteTv(object : RemoteDataSource.LoadTvCallback {
            override fun onAllTvReceived(responseTv: List<ItemsItem>) {
                val list = ArrayList<TvEntity>()
                for (response in responseTv) {
                    val tv = TvEntity(
                        response.id,
                        response.name,
                        response.overview,
                        response.posterPath
                    )
                    list.add(tv)
                }
                responseResult.postValue(list)
            }
        })
        return responseResult
    }

    override fun getDetailTv(tvId: Int): LiveData<List<TvEntity>> {
        val responseResult = MutableLiveData<List<TvEntity>>()
        remoteDataSource.getRemoteTv(object : RemoteDataSource.LoadTvCallback {
            override fun onAllTvReceived(responseTv: List<ItemsItem>) {
                val list = ArrayList<TvEntity>()
                for (response in responseTv) {
                    if (response.id == tvId) {
                        val tv = TvEntity(
                            response.id,
                            response.name,
                            response.overview,
                            response.posterPath
                        )
                        list.add(tv)
                    }
                    responseResult.postValue(list)
                }
            }

        })
        return responseResult
    }

}