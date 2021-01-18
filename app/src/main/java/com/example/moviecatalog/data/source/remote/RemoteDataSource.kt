package com.example.moviecatalog.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviecatalog.data.source.remote.response.ItemsItem
import com.example.moviecatalog.data.source.remote.response.ResponseMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiConfig: ApiConfig) {
    companion object {
        private const val LIST_MOVIE_ID: String = "7070136"
        private const val LIST_TV_ID: String = "7070818"
        private const val TAG = "RemoteDataSource"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiConfig: ApiConfig): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource(apiConfig)
        }
    }

    fun getRemoteMovies(): LiveData<ApiResponse<List<ItemsItem>>> {
        val client = apiConfig.getApiService().getList(LIST_MOVIE_ID)
        val resultMovie = MutableLiveData<ApiResponse<List<ItemsItem>>>()
        client.enqueue(object : Callback<ResponseMovies> {
            override fun onResponse(
                call: Call<ResponseMovies>,
                response: Response<ResponseMovies>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        resultMovie.value = ApiResponse.success(it.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseMovies>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
        return resultMovie
    }

    fun getRemoteTv(): LiveData<ApiResponse<List<ItemsItem>>> {
        val client = apiConfig.getApiService().getList(LIST_TV_ID)
        val resultTv = MutableLiveData<ApiResponse<List<ItemsItem>>>()
        client.enqueue(object : Callback<ResponseMovies> {
            override fun onResponse(
                call: Call<ResponseMovies>,
                response: Response<ResponseMovies>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        resultTv.value = ApiResponse.success(it.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseMovies>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
        return resultTv
    }

    interface LoadMoviesCallback {
        fun onAllMoviesReceived(responseMovies: List<ItemsItem>)
    }

    interface LoadTvCallback {
        fun onAllTvReceived(responseTv: List<ItemsItem>)
    }
}