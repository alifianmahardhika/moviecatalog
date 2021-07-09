package com.example.moviecatalog.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.moviecatalog.data.MovieRepository
import com.example.moviecatalog.data.source.local.entity.TvEntity
import com.example.moviecatalog.vo.Resource
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
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvEntity>

    @Before
    fun setUp() {
        viewModel = TvViewModel(movieRepository)
    }

    @Test
    fun getTv() {
        val dummyTv = Resource.success(pagedList)

        `when`(dummyTv.data?.size).thenReturn(11)
        val tvs = MutableLiveData<Resource<PagedList<TvEntity>>>()
        tvs.value = dummyTv

        `when`(movieRepository.getAllTv()).thenReturn(tvs)
        val tventities = viewModel.getTvs().value?.data
        verify(movieRepository).getAllTv()
        assertNotNull(tventities)
        assertEquals(11, tventities?.size)

        viewModel.getTvs().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}