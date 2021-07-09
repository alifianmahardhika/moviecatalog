package com.example.moviecatalog.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.moviecatalog.data.TvEntity
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
class TvViewModelTest {

    private lateinit var viewModel: TvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<List<TvEntity>>

    @Before
    fun setUp() {
        viewModel = TvViewModel(repository)
    }

    @Test
    fun getTv() {
        val dummyTv = DataDummy.generateDummyTv()
        val tvs = MutableLiveData<List<TvEntity>>()
        tvs.value = dummyTv

        `when`(repository.getAllTv()).thenReturn(tvs)
        val tv = viewModel.getTvs().value
        verify(repository).getAllTv()
        assertNotNull(tv)
        assertEquals(11, tv?.size)

        viewModel.getTvs().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}