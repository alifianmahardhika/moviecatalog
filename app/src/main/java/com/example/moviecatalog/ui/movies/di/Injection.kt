package com.example.moviecatalog.ui.movies.di

import android.content.Context
import com.example.moviecatalog.data.source.MovieRepository
import com.example.moviecatalog.data.source.remote.ApiConfig
import com.example.moviecatalog.data.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val remoteDataSource = RemoteDataSource.getInstance(apiConfig = ApiConfig())
        return MovieRepository.getInstance(remoteDataSource)
    }
}