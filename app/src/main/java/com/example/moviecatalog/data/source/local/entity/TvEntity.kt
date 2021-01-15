package com.example.moviecatalog.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvtable")
data class TvEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvId")
    val tvId: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "poster")
    val poster: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
