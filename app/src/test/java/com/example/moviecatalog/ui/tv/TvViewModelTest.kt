package com.example.moviecatalog.ui.tv

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class TvViewModelTest {
    private lateinit var viewModel: TvViewModel

    @Before
    fun setUp() {
        viewModel = TvViewModel()
    }

    @Test
    fun getTvs() {
        val tvs = viewModel.getTvs()
        assertNotNull(tvs)
        assertEquals(11, tvs.size)
    }
}