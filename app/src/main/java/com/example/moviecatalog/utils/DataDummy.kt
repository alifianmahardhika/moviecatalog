package com.example.moviecatalog.utils

import com.example.moviecatalog.data.MovieEntity
import com.example.moviecatalog.data.TvEntity
import com.example.moviecatalog.data.source.remote.response.ItemsItem

object DataDummy {
    fun generateDummyMovie(): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()
        for (i in 1..11) {
            movies.add(
                MovieEntity(
                    i, "Judul", "Deskripsi", "/poster"
                )
            )
        }
        return movies
    }

    fun generateDummyTv(): List<TvEntity> {
        val tvs = ArrayList<TvEntity>()
        for (i in 1..11) {
            tvs.add(
                TvEntity(
                    i, "Judul", "Deskripsi", "/poster"
                )
            )
        }
        return tvs
    }

    fun generateRemoteDummy(): List<ItemsItem> {
        val remoteDummy = ArrayList<ItemsItem>()
        for (i in 1..11) {
            remoteDummy.add(
                ItemsItem(
                    "dummYOverview",
                    "dummyTitle",
                    "/posterDummy",
                    i,
                    "dummyName"
                )
            )
        }
        return remoteDummy
    }
}