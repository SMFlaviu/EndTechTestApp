package com.flaviu.endtechtestapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flaviu.endtechtestapp.ui.MainActivity
import com.flaviu.endtechtestapp.ui.adapter.ProductsAdapter.ProductsViewHolder
import com.flaviu.endtechtestapp.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ProductsInstrumentedTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }


    @Test
    fun whenRecyclerViewIsVisible() {
        onView(withId(R.id.products_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun checkItemsRecyclerView() {
        onView(withId(R.id.products_recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<ProductsViewHolder>(
                1
            )
        )
        onView(withId(R.id.products_recycler_view))
            .perform(actionOnItemAtPosition<ProductsViewHolder>(2, click()))
    }
}