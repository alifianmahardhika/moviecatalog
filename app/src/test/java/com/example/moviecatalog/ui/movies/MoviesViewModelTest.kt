package com.example.moviecatalog.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.moviecatalog.data.MovieEntity
import com.example.moviecatalog.data.source.MovieRepository
import com.example.moviecatalog.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(repository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = DataDummy.generateDummyMovie()
        val movies = MutableLiveData<List<MovieEntity>>()
        movies.value = dummyMovies

        `when`(repository.getAllMovies()).thenReturn(movies)
        val movie = viewModel.getMovies().value
        verify(repository).getAllMovies()
        assertNotNull(movie)
        assertEquals(11, movie?.size)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}