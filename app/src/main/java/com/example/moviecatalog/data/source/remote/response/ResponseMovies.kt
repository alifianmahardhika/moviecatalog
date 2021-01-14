package com.example.moviecatalog.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseMovies(

    @field:SerializedName("items")
    val items: List<ItemsItem>,
)

data class ItemsItem(

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String
)
