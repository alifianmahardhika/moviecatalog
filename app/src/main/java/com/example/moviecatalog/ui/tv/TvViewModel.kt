package com.example.moviecatalog.ui.tv

import androidx.lifecycle.ViewModel
import com.example.moviecatalog.data.TvEntity
import com.example.moviecatalog.utils.Data

class TvViewModel : ViewModel() {
    fun getTvs(): List<TvEntity> = Data.generateTvsData()
}