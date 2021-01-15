package com.example.moviecatalog.ui.movies.di

import com.example.moviecatalog.data.MovieRepository
import com.example.moviecatalog.data.source.remote.ApiConfig
import com.example.moviecatalog.data.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(): MovieRepository {
        val remoteDataSource = RemoteDataSource.getInstance(apiConfig = ApiConfig())
        return MovieRepository.getInstance(remoteDataSource)
    }
}