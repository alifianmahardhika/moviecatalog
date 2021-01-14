package com.example.moviecatalog.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviecatalog.data.source.remote.RemoteDataSource
import com.example.moviecatalog.utils.DataDummy
import com.example.moviecatalog.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class MovieRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val academyRepository = FakeMovieRepository(remote)

    private val movieResponse = DataDummy.generateRemoteDummy()

    @Test
    fun getAllMovies() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMoviesCallback)
                .onAllMoviesReceived(movieResponse)
            null
        }.`when`(remote).getRemoteMovies(any())
        val movie = LiveDataTestUtil.getValue(academyRepository.getAllMovies())
        verify(remote).getRemoteMovies(any())
        assertNotNull(movie)
        assertEquals(movieResponse.size.toLong(), movie.size.toLong())
    }
}