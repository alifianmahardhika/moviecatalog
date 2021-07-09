package com.example.moviecatalog.data.source.remote

import com.example.moviecatalog.BuildConfig
import com.example.moviecatalog.data.source.remote.response.ResponseMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("list/{list_id}?api_key=" + BuildConfig.TMDB_TOKEN)
    fun getList(
        @Path("list_id") list_id: String
    ): Call<ResponseMovies>
}