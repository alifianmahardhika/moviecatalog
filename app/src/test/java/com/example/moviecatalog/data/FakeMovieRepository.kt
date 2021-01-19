package com.example.moviecatalog.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviecatalog.data.source.local.LocalDataSource
import com.example.moviecatalog.data.source.local.entity.MovieEntity
import com.example.moviecatalog.data.source.local.entity.TvEntity
import com.example.moviecatalog.data.source.remote.ApiResponse
import com.example.moviecatalog.data.source.remote.RemoteDataSource
import com.example.moviecatalog.data.source.remote.response.ItemsItem
import com.example.moviecatalog.utils.AppExecutors
import com.example.moviecatalog.vo.Resource

class FakeMovieRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDataSource {

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(4)
        .setPageSize(4)
        .build()

    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<ItemsItem>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<ItemsItem>>> {
                return remoteDataSource.getRemoteMovies()
            }

            override fun saveCallResult(data: List<ItemsItem>) {
                val list = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        response.id,
                        response.title, response.overview,
                        response.posterPath
                    )
                    list.add(movie)
                }

                localDataSource.insertMovies(list)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(movieId: Int): LiveData<Resource<MovieEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllTv(): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, List<ItemsItem>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                return LivePagedListBuilder(localDataSource.getAllTvs(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<ItemsItem>>> {
                return remoteDataSource.getRemoteTv()
            }

            override fun saveCallResult(data: List<ItemsItem>) {
                val list = ArrayList<TvEntity>()
                for (response in data) {
                    val tv = TvEntity(
                        response.id,
                        response.name, response.overview,
                        response.posterPath
                    )
                    list.add(tv)
                }

                localDataSource.insertTvs(list)
            }
        }.asLiveData()
    }

    override fun getDetailTv(tvId: Int): LiveData<Resource<TvEntity>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteTv(): LiveData<PagedList<TvEntity>> {
        TODO("Not yet implemented")
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setFavoriteTv(tv: TvEntity, state: Boolean) {
        TODO("Not yet implemented")
    }
}