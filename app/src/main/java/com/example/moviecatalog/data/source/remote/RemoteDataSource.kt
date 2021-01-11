package com.example.moviecatalog.data.source.remote

import android.util.Log
import com.example.moviecatalog.data.source.remote.response.ItemsItem
import com.example.moviecatalog.data.source.remote.response.ResponseMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiConfig: ApiConfig) {
    companion object {
        private const val LIST_ID: String = "7070136"
        private const val TAG = "RemoteDataSource"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiConfig: ApiConfig): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource(apiConfig)
        }
    }

    fun getRemoteMovies(callback: LoadMoviesCallback) {
        val client = apiConfig.getApiService().getMoviesList(LIST_ID)
        client.enqueue(object : Callback<ResponseMovies> {
            override fun onResponse(
                call: Call<ResponseMovies>,
                response: Response<ResponseMovies>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback.onAllMoviesReceived(it.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseMovies>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    interface LoadMoviesCallback {
        fun onAllMoviesReceived(responseMovies: List<ItemsItem>)
    }
}