package com.example.moviecatalog.di

import android.content.Context
import com.example.moviecatalog.data.MovieRepository
import com.example.moviecatalog.data.source.local.LocalDataSource
import com.example.moviecatalog.data.source.local.room.MovieDatabase
import com.example.moviecatalog.data.source.remote.ApiConfig
import com.example.moviecatalog.data.source.remote.RemoteDataSource
import com.example.moviecatalog.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(apiConfig = ApiConfig())
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()
        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}