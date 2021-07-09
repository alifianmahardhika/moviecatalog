package com.example.moviecatalog.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.example.moviecatalog.data.source.local.LocalDataSource
import com.example.moviecatalog.data.source.local.entity.MovieEntity
import com.example.moviecatalog.data.source.remote.RemoteDataSource
import com.example.moviecatalog.utils.AppExecutors
import com.example.moviecatalog.utils.DataDummy
import com.example.moviecatalog.utils.PagedListUtil
import com.example.moviecatalog.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieRepository = FakeMovieRepository(remote, local, appExecutors)

    private val movieResponse = DataDummy.generateRemoteDummy()

    @Suppress("UNCHECKED_CAST")

    @Test
    fun getAllMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieRepository.getAllMovies()

        val movieentities =
            Resource.success(PagedListUtil.mockPagedList((DataDummy.generateDummyMovie())))
        verify(local).getAllMovies()
        assertNotNull(movieentities.data)
        assertEquals(movieResponse.size.toLong(), movieentities.data?.size?.toLong())
    }
}