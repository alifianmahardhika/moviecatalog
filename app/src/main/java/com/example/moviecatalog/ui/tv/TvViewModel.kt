package com.example.moviecatalog.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.MovieRepository
import com.example.moviecatalog.data.source.local.entity.TvEntity

class TvViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getTvs(): LiveData<List<TvEntity>> = movieRepository.getAllTv()
    fun getDetailTv(itemId: Int): LiveData<List<TvEntity>> = movieRepository.getDetailTv(itemId)
}