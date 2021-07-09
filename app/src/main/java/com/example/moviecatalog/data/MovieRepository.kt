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

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MovieDataSource {
    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors)
            }
    }

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
        return object : NetworkBoundResource<MovieEntity, List<ItemsItem>>(appExecutors) {
            public override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getDetailMovie(movieId)
            }

            public override fun shouldFetch(data: MovieEntity?): Boolean {
                return false
            }

            public override fun createCall(): LiveData<ApiResponse<List<ItemsItem>>> {
                return remoteDataSource.getRemoteMovies()
            }

            public override fun saveCallResult(data: List<ItemsItem>) {
                val list = ArrayList<MovieEntity>()
                for (response in data) {
                    if (response.id == movieId) {
                        val movie = MovieEntity(
                            response.id,
                            response.title,
                            response.overview,
                            response.posterPath
                        )
                        list.add(movie)
                    }
                }
                localDataSource.insertMovies(list)
            }
        }.asLiveData()
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
        return object : NetworkBoundResource<TvEntity, List<ItemsItem>>(appExecutors) {
            public override fun loadFromDB(): LiveData<TvEntity> {
                return localDataSource.getDetailTv(tvId)
            }

            public override fun shouldFetch(data: TvEntity?): Boolean {
                return false
            }

            public override fun createCall(): LiveData<ApiResponse<List<ItemsItem>>> {
                return remoteDataSource.getRemoteTv()
            }

            public override fun saveCallResult(data: List<ItemsItem>) {
                val list = ArrayList<TvEntity>()
                for (response in data) {
                    if (response.id == tvId) {
                        val tv = TvEntity(
                            response.id,
                            response.name,
                            response.overview,
                            response.posterPath
                        )
                        list.add(tv)
                    }
                }
                localDataSource.insertTvs(list)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getFavoriteTv(): LiveData<PagedList<TvEntity>> {
        return LivePagedListBuilder(localDataSource.getFavoriteTvs(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        return appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }
    }

    override fun setFavoriteTv(tv: TvEntity, state: Boolean) {
        return appExecutors.diskIO().execute { localDataSource.setFavoriteTv(tv, state) }
    }
}