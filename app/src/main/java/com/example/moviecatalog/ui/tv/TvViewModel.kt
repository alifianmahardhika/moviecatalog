package com.example.moviecatalog.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviecatalog.data.MovieRepository
import com.example.moviecatalog.data.source.local.entity.TvEntity
import com.example.moviecatalog.vo.Resource

class TvViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getTvs(): LiveData<Resource<PagedList<TvEntity>>> = movieRepository.getAllTv()
    fun getFavoriteTv(): LiveData<PagedList<TvEntity>> = movieRepository.getFavoriteTv()
    val tvId = MutableLiveData<Int>()

    fun setTvId(Id: Int) {
        this.tvId.value = Id
    }

    var selectedTv: LiveData<Resource<TvEntity>> = Transformations.switchMap(tvId) { mTvId ->
        movieRepository.getDetailTv(mTvId)
    }

    fun setFavorite() {
        val tvRes = selectedTv.value
        if (tvRes != null) {
            val tvEntity = tvRes.data
            if (tvEntity != null) {
                val newState = !tvEntity.isFavorite
                movieRepository.setFavoriteTv(tvEntity, newState)
            }
        }
    }
}