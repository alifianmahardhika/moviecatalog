package com.example.moviecatalog.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.moviecatalog.R
import com.example.moviecatalog.utils.DataDummy
import org.junit.Before
import org.junit.Test

class HomeActivityTest {
    private val dummyMovies = DataDummy.generateDummyMovie()

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovies.size
            )
        )
    }


    @Test
    fun loadDetailMovie() {
        delay()
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        delay()
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_desc)).check(matches(isDisplayed()))
    }

    private fun delay() {
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}